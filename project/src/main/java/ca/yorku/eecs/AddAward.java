package ca.yorku.eecs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

import java.io.IOException;

public class AddAward implements HttpHandler {
    public AddAward() {}

    public void handle(HttpExchange r) {
        try {
            if (r.getRequestMethod().equals("PUT")) {
                handlePut(r);
            } else {
                r.sendResponseHeaders(404, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handlePut(HttpExchange r) throws IOException, JSONException {
        // Convert the request body to a string
        String body = Utils.convert(r.getRequestBody());

        // Deserialize the request body into a JSON object
        JSONObject deserialized = new JSONObject(body);

        // Variables to hold the HTTP status code, the name and awardId of the award
        int statusCode = 0;
        String awardName = "";
        String awardId = "";
        String actorId = "";  // Added actorId to link awards with actors

        // Check if the required information is present in the body. If not, raise error 400
        if (deserialized.has("name"))
            awardName = deserialized.getString("name");
        else
            statusCode = 400;

        if (deserialized.has("awardId"))
            awardId = deserialized.getString("awardId");
        else
            statusCode = 400;

        // Added check for actorId to create relationships
        if (deserialized.has("actorId"))
            actorId = deserialized.getString("actorId");
        else
            statusCode = 400;

        if (statusCode == 0) {
            try (Transaction tx = Utils.driver.session().beginTransaction()) {
                // Check if there is any data with the same awardId
                StatementResult result = tx.run("MATCH (w:Award {awardId: $awardId}) RETURN w",
                        org.neo4j.driver.v1.Values.parameters("awardId", awardId));

                // Check for duplicate entries
                if (!result.hasNext()) {
                    // Create the award if it does not exist
                    tx.run("CREATE (w:Award {name: $awardName, awardId: $awardId})",
                            org.neo4j.driver.v1.Values.parameters("awardName", awardName, "awardId", awardId));
                }

                // Added relationship creation between actor and award
                tx.run("MATCH (a:Actor {actorId: $actorId}), (w:Award {awardId: $awardId}) " +
                                "MERGE (a)-[:WON]->(w)",
                        org.neo4j.driver.v1.Values.parameters("actorId", actorId, "awardId", awardId));

                // Commit the query for persistence
                tx.success();

                System.out.println("Award added: " + awardName + " to actor: " + actorId);
                statusCode = 200;
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                statusCode = 500;
            }
        }

        r.sendResponseHeaders(statusCode, -1);
    }
}
