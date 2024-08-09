package ca.yorku.eecs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GetAward implements HttpHandler {

    // Constructor for the GetAward handler
    public GetAward() {
        System.out.println("GetAward handler initialized");
    }

    // This method handles incoming HTTP requests
    public void handle(HttpExchange r) {
        try {
            System.out.println("Handling request: " + r.getRequestMethod());
            // We only handle GET requests in this handler
            if (r.getRequestMethod().equals("GET")) {
                handleGet(r);  // Process the GET request
            } else {
                // If the method isn't GET, respond with 404 (Not Found)
                r.sendResponseHeaders(404, -1);
                r.getResponseBody().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This method processes the GET requests
    public void handleGet(HttpExchange r) throws IOException, JSONException {
        System.out.println("Processing GET request");

        // Read and convert the request body to a string
        String body = Utils.convert(r.getRequestBody());
        System.out.println("Request Body: " + body);

        // check if the request body is empty
        if(body.isEmpty()) {
            String uri = r.getRequestURI().toString().split("\\?jsonString=")[1];
            body = URLDecoder.decode(uri, StandardCharsets.UTF_8);
            System.out.println("Request URI: " + body);
        }

        // Parse the request body string into a JSON object
        JSONObject deserialized = new JSONObject(body);

        // Variables to hold the HTTP status code and the awardName from the request
        int statusCode = 0;
        String awardName = "";

        // Check if the awardName is present in the request body. If not, set status code to 400 (Bad Request)
        if (deserialized.has("name")) {
            awardName = deserialized.getString("name");
        } else {
            statusCode = 400;  // Bad request because awardName is missing
        }

        // If no issues with the request, proceed to query the database
        if (statusCode == 0) {
            // Begin a transaction to query the Neo4j database
            try (Transaction tx = Utils.driver.session().beginTransaction()) {
                // Query to find the award by awardName and return its name and the list of actors who won it
                StatementResult result = tx.run("MATCH (a:actor)-[:HAS_AWARD]->(aw:award {Name: $awardName}) " +
                                "RETURN aw.Name AS awardName, collect({name: a.Name, actorId: a.id}) AS actors",
                        org.neo4j.driver.v1.Values.parameters("awardName", awardName));

                System.out.println("Query executed. Checking result...");

                // Check if the award exists in the query result
                if (result.hasNext()) {
                    System.out.println("Result hasNext: true");
                    Record record = result.next();
                    // Retrieve the award's details from the query result
                    JSONArray actorsArray = new JSONArray();
                    for (Object actorObj : record.get("actors").asList()) {
                        Map<String, Object> actorMap = (Map<String, Object>) actorObj;
                        JSONObject actorJson = new JSONObject();
                        actorJson.put("actorName", actorMap.get("name").toString());
                        actorJson.put("actorId", actorMap.get("actorId").toString());
                        actorsArray.put(actorJson);
                    }

                    // Prepare the JSON response with the award details
                    JSONObject response = new JSONObject();
                    response.put("awardName", awardName);
                    response.put("actors", actorsArray);

                    // Send the response with status 200 (OK)
                    r.getResponseHeaders().add("Content-Type", "application/json");
                    r.sendResponseHeaders(200, response.toString().getBytes().length);
                    r.getResponseBody().write(response.toString().getBytes());
                    r.getResponseBody().close();
                    return;
                } else {
                    System.out.println("Result hasNext: false");
                    // If the award is not found, set status code to 404 (Not Found)
                    statusCode = 404;
                }

                tx.success();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                statusCode = 500;  // Internal server error
            }
        }

        // If status code is not 200, send the appropriate error response
        if (statusCode != 200) {
            r.sendResponseHeaders(statusCode, -1);
            r.getResponseBody().close();
        }
    }
}
