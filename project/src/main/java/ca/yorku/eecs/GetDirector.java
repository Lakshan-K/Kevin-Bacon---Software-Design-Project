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

public class GetDirector implements HttpHandler {

    public GetDirector() {
        System.out.println("GetDirector handler initialized");
    }

    // Handle method to manage incoming HTTP requests
    public void handle(HttpExchange r) {
        try {
            System.out.println("Handling request: " + r.getRequestMethod());
            // Check if the request method is GET
            if (r.getRequestMethod().equals("GET")) {
                handleGet(r);  // Process the GET request
            } else {
                // Respond with 404 if the method is not GET
                r.sendResponseHeaders(404, -1);
                r.getResponseBody().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle GET requests
    public void handleGet(HttpExchange r) throws IOException, JSONException {
        System.out.println("Processing GET request");

        // Convert the request body to a String
        String body = Utils.convert(r.getRequestBody());
        System.out.println("Request Body: " + body);

        // Deserialize the request body into a JSON object
        JSONObject deserialized = new JSONObject(body);

        // Variables to hold the HTTP status code and directorID
        int statusCode = 0;
        String directorId = "";

        // Check if the directorId is present in the request body
        if (deserialized.has("directorId")) {
            directorId = deserialized.getString("directorId");
        } else {
            // If directorId is missing, set status code to 400 (Bad Request)
            statusCode = 400;
        }
        // Proceed if there are no errors so far
        if (statusCode == 0) {
            // Start a transaction to query the database
            try (Transaction tx = Utils.driver.session().beginTransaction()) {
                // Query to find the director by directorId and get their details
                StatementResult result = tx.run("MATCH (a:Director {directorId: $directorId}) RETURN a.name AS name, " +
                                "[ (a)-[:Directed]->(m:Movie) | m.movieId ] AS movies",
                        org.neo4j.driver.v1.Values.parameters("directorId", directorId));

                System.out.println("Query executed. Checking result...");

                // Check if the director exists
                if (result.hasNext()) {
                    System.out.println("Result hasNext: true");
                    Record record = result.next();
                    // Get the director's details
                    String name = record.get("name").asString();
                    JSONArray movies = new JSONArray(record.get("movies").asList());

                    // Prepare the response JSON
                    JSONObject response = new JSONObject();
                    response.put("directorId", directorId);
                    response.put("name", name);
                    response.put("movies", movies);

                    // Send the response with a 200 status code
                    r.getResponseHeaders().add("Content-Type", "application/json");
                    r.sendResponseHeaders(200, response.toString().getBytes().length);
                    r.getResponseBody().write(response.toString().getBytes());
                    r.getResponseBody().close();
                    return;
                } else {
                    System.out.println("Result hasNext: false");
                    // If the director isn't found, set status code to 404 (Not Found)
                    statusCode = 404;
                }

                tx.success();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                // If there's an exception, set status code to 500 (Internal Server Error)
                statusCode = 500;
            }
        }
        // If the status code isn't 200, send the appropriate error response
        if (statusCode != 200) {
            r.sendResponseHeaders(statusCode, -1);
            r.getResponseBody().close();
        }
    }
}
