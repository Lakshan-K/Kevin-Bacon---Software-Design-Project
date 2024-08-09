package ca.yorku.eecs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

import java.io.IOException;

public class AddCoActorRelationship implements HttpHandler {
    public AddCoActorRelationship() {}

    public void handle(HttpExchange r) {
        try {
            if (r.getRequestMethod().equals("PUT")) {
                handlePut(r);
            }else{
                r.sendResponseHeaders(404, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handlePut(HttpExchange r) throws IOException, JSONException {
        // convert the request body
        String body = Utils.convert(r.getRequestBody());

        // get the deserialized JSON
        JSONObject deserialized = new JSONObject(body);

        // variables to hold the HTTP status code, and the ID of actor and co-actor
        int statusCode = 0;
        String actorId = "";
        String coActorId = "";

        // check if the required information is present in the body. If not, raise error 400
        if (deserialized.has("actorId")) {
            actorId = deserialized.getString("actorId");

            // if actorId is empty, raise an error
            if (actorId.isEmpty()) {
                statusCode = 400;
            }
        } else
            statusCode = 400;

        if (deserialized.has("coActorId")) {
            coActorId = deserialized.getString("coActorId");

            // if actorId is empty, raise an error
            if (coActorId.isEmpty()) {
                statusCode = 400;
            }
        } else
            statusCode = 400;

        // check if actorID and coActorID are not equal
        if (actorId.equalsIgnoreCase(coActorId)) {
            statusCode = 400;
        }

        if (statusCode == 0) {
            try (Transaction tx = Utils.driver.session().beginTransaction()) {
                StatementResult result = tx.run("MATCH (a:actor {actorId: $actorId})\n" +
                        "MATCH (b:actor {actorId: $coActorId})\n" +
                        "RETURN a, b", org.neo4j.driver.v1.Values.parameters("actorId", actorId, "coActorId", coActorId));

                // check if the result has value which indicates that the actorID and coActorID exists.
                // if empty, then either of them or both do no exist
                if (!result.hasNext()) {
                    statusCode = 404;
                } else {

                    // check if there is any existing relationship between actorId and coActorId
                    result = tx.run("MATCH (a:actor {actorId: $actorId})-[r:ACTED_WITH]->(b:actor {actorId: $coActorId}) " +
                            "RETURN r", org.neo4j.driver.v1.Values.parameters("actorId", actorId, "coActorId", coActorId));

                    // check for duplicate entries
                    if (result.hasNext()) {
                        statusCode = 400;
                    } else {
                        // make the query
                        tx.run("MATCH (a:actor {actorId: $actorId}), (b:actor {actorId: $coActorId}) " +
                                        "CREATE (a)-[r:ACTED_WITH]->(b)",
                                org.neo4j.driver.v1.Values.parameters("actorId", actorId, "coActorId", coActorId));

                        // commit the query for persistence
                        tx.success();

                        System.out.println("CoActor Relation added");
                        statusCode = 200;
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                statusCode = 500;
            }
        }

        r.sendResponseHeaders(statusCode, -1);
    }
}
