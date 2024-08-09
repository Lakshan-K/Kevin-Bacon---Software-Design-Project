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
import java.nio.charset.StandardCharsets;

public class GetMovie implements HttpHandler {

    public GetMovie() {
        System.out.println("GetMovie handler initialized");
    }

    public void handle(HttpExchange r) {
        try {
            System.out.println("Handling request: " + r.getRequestMethod());
            if (r.getRequestMethod().equals("GET")) {
                handleGet(r);
            } else {
                r.sendResponseHeaders(404, -1); // Respond with 404 if it's not a GET request
                r.getResponseBody().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleGet(HttpExchange r) throws IOException, JSONException {
        System.out.println("Processing GET request");
        // Convert the request body to a string
        String body = Utils.convert(r.getRequestBody());
        System.out.println("Request Body: " + body);

        // check if the request body is empty
        if(body.isEmpty()) {
            String uri = r.getRequestURI().toString().split("\\?jsonString=")[1];
            body = URLDecoder.decode(uri, StandardCharsets.UTF_8);
            System.out.println("Request URI: " + body);
        }

        // Deserialize the JSON from the request body
        JSONObject deserialized = new JSONObject(body);

        // Initialize variables for the status code and movieId
        int statusCode = 0;
        String movieId = "";

        // Check if the movieId is present in the request body; if not, set status code to 400
        if (deserialized.has("movieId")) {
            movieId = deserialized.getString("movieId");
        } else {
            statusCode = 400;
        }

        // If everything is okay so far, proceed to query the database
        if (statusCode == 0) {
            try (Transaction tx = Utils.driver.session().beginTransaction()) {
                // Query to find the movie by movieId and get its details
                StatementResult result = tx.run("MATCH (m:movie {id: $movieId}) RETURN m.Name AS name, " +
                                "[ (m)<-[:ACTED_IN]-(a:actor) | a.id ] AS actors",
                        org.neo4j.driver.v1.Values.parameters("movieId", movieId));

                System.out.println("Query executed. Checking result...");

                // Check if the movie exists
                if (result.hasNext()) {
                    System.out.println("Result hasNext: true");
                    Record record = result.next();
                    // Retrieve the movie's details
                    String name = record.get("name").asString();
                    JSONArray actors = new JSONArray(record.get("actors").asList());

                    // Prepare the response JSON
                    JSONObject response = new JSONObject();
                    response.put("movieId", movieId);
                    response.put("name", name);
                    response.put("actors", actors);

                    // Send the response with a 200 status code
                    r.getResponseHeaders().add("Content-Type", "application/json");
                    r.sendResponseHeaders(200, response.toString().getBytes().length);
                    r.getResponseBody().write(response.toString().getBytes());
                    r.getResponseBody().close();
                    return;
                } else {
                    System.out.println("Result hasNext: false");
                    // If the movie isn't found, set status code to 404
                    statusCode = 404;
                }

                tx.success();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                // If there's an exception, set status code to 500
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
