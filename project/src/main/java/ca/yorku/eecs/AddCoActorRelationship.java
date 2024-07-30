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
        if (deserialized.has("actorId"))
            actorId = deserialized.getString("actorId");
        else
            statusCode = 400;

        if (deserialized.has("coActorId"))
            coActorId = deserialized.getString("coActorId");
        else
            statusCode = 400;

        try (Transaction tx = Utils.driver.session().beginTransaction()) {
            // check if there is any existing relationship between actorId and coActorId
            StatementResult result = tx.run("MATCH (a:Actor {actorId: $actorId})-[r:ACTED_WITH]->(b:Actor {actorId: $coActorId}) " +
                    "RETURN r", org.neo4j.driver.v1.Values.parameters("actorId", actorId, "coActorId", coActorId));

            // check for duplicate entries
            if (result.hasNext()) {
                statusCode = 400;
            } else {
                // make the query
                tx.run("MATCH (a:Actor {actorId: $actorId}), (b:Actor {actorId: $coActorId}) " +
                                "CREATE (a)-[r:ACTED_WITH]->(b)",
                        org.neo4j.driver.v1.Values.parameters("actorId", actorId, "coActorId", coActorId));

                // commit the query for persistence
                tx.success();

                System.out.println("CoActor Relation added");
                statusCode = 200;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            statusCode = 500;
        }

        r.sendResponseHeaders(statusCode, -1);
    }
}
