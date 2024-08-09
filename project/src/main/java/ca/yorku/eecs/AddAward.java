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
            System.out.println(e);
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
        String actorId = "";

        // Check if the required information is present in the body. If not, raise error 400
        if (deserialized.has("name"))
            awardName = deserialized.getString("name");
        else
            statusCode = 400;

        if (deserialized.has("awardId"))
            awardId = deserialized.getString("awardId");
        else
            statusCode = 400;

        if (deserialized.has("actorId"))
            actorId = deserialized.getString("actorId");
        else
            statusCode = 400;

        if (statusCode == 0) {
            try (Transaction tx = Utils.driver.session().beginTransaction()) {
                // Check if there is any data with the same awardId
                StatementResult result = tx.run("MATCH (w:award {id: $awardId}) RETURN w",
                        org.neo4j.driver.v1.Values.parameters("awardId", awardId));

                // Check for duplicate entries
                if(result.hasNext()) {
                    // Award already exists, so just create the relationship if it doesn't exist
                    StatementResult relationshipResult = tx.run("MATCH (a:actor {id: $actorId})-[:HAS_AWARD]->(w:award {id: $awardId}) RETURN a",
                            org.neo4j.driver.v1.Values.parameters("actorId", actorId, "awardId", awardId));

                    if (!relationshipResult.hasNext()) {
                        // Create the relationship between the actor and the award
                        tx.run("MATCH (a:actor {id: $actorId}), (w:award {id: $awardId}) " +
                                        "MERGE (a)-[:HAS_AWARD]->(w)",
                                org.neo4j.driver.v1.Values.parameters("actorId", actorId, "awardId", awardId));
                    } else {
                        statusCode = 400;  // Relationship already exists
                    }
                } else {
                    // Award doesn't exist, create it and then create the relationship
                    tx.run("CREATE (w:award {Name: $awardName, id: $awardId})",
                            org.neo4j.driver.v1.Values.parameters("awardName", awardName, "awardId", awardId));

                    // Create the relationship between the actor and the award
                    tx.run("MATCH (a:actor {id: $actorId}), (w:Award {id: $awardId}) " +
                                    "MERGE (a)-[:HAS_AWARD]->(w)",
                            org.neo4j.driver.v1.Values.parameters("actorId", actorId, "awardId", awardId));
                }

                // only commit if the status code is not 400
                if(statusCode != 400) {
                    // Commit the query for persistence
                    tx.success();

                    System.out.println("Award added: " + awardName);
                    statusCode = 200;
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                statusCode = 500;
            }
        }

        r.sendResponseHeaders(statusCode, -1);
    }
}
