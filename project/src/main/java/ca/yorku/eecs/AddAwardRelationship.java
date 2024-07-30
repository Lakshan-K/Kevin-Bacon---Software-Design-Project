package ca.yorku.eecs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

import java.io.IOException;

public class AddAwardRelationship implements HttpHandler {
    public AddAwardRelationship() {}

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

        // variables to hold the HTTP status code, and the ID of the actor and the award they won
        int statusCode = 0;
        String actorId = "";
        String awardId = "";

        // check if the required information is present in the body. If not, raise error 400
        if (deserialized.has("actorId"))
            actorId = deserialized.getString("actorId");
        else
            statusCode = 400;

        if (deserialized.has("awardId"))
            awardId = deserialized.getString("awardId");
        else
            statusCode = 400;

        try (Transaction tx = Utils.driver.session().beginTransaction()) {
            // check if there is any existing relationship between actorId and awardId
            StatementResult result = tx.run("MATCH (a:Actor {actorId: $actorId})-[r:WON]->(w:Award {awardId: $awardId}) " +
                    "RETURN r", org.neo4j.driver.v1.Values.parameters("actorId", actorId, "awardId", awardId));

            // check for duplicate entries
            if (result.hasNext()) {
                statusCode = 400;
            } else {
                // make the query
                tx.run("MATCH (a:Actor {actorId: $actorId}), (w:Award {awardId: $awardId}) " +
                                "CREATE (a)-[r:WON]->(w)",
                        org.neo4j.driver.v1.Values.parameters("actorId", actorId, "awardId", awardId));

                // commit the query for persistence
                tx.success();

                System.out.println("Award Relation added");
                statusCode = 200;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            statusCode = 500;
        }

        r.sendResponseHeaders(statusCode, -1);
    }
}
