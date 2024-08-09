package ca.yorku.eecs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

import java.io.IOException;

public class ComputeBaconNumber implements HttpHandler {
    private static final String KEVIN_BACON_ID = "nm0000102";
    public ComputeBaconNumber() {
        System.out.println("ComputeBaconNumber handler initialized");
    }

    // Handle method to manage incoming HTTP requests
    public void handle(HttpExchange r) {
        try {
            System.out.println("Handling request: " + r.getRequestMethod());
            if (r.getRequestMethod().equals("GET")) {
                handleGet(r);  // Process the GET request
            } else {
                r.sendResponseHeaders(404, -1);
                r.getResponseBody().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle GET requests
    public void handleGet(HttpExchange r) throws IOException, JSONException {
        String body = Utils.convert(r.getRequestBody());
        JSONObject deserialized = new JSONObject(body);

        String actorId = "";
        int statusCode = 0;

        if (deserialized.has("actorId")) {
            actorId = deserialized.getString("actorId");
        } else {
            statusCode = 400;  // Bad Request
        }

        if (statusCode == 0) {
            try (Transaction tx = Utils.driver.session().beginTransaction()) {
                String query = "MATCH (bacon:Actor {actorId: $baconId}), (actor:Actor {actorId: $actorId}), " +
                        "p = shortestPath((actor)-[:ACTED_IN*]-(bacon)) " +
                        "RETURN length(p)/2 AS baconNumber";

                StatementResult result = tx.run(query, org.neo4j.driver.v1.Values.parameters(
                        "actorId", actorId,
                        "baconId", KEVIN_BACON_ID
                ));

                if (result.hasNext()) {
                    Record record = result.next();
                    int baconNumber = record.get("baconNumber").asInt();

                    JSONObject response = new JSONObject();
                    response.put("baconNumber", baconNumber);

                    r.getResponseHeaders().add("Content-Type", "application/json");
                    r.sendResponseHeaders(200, response.toString().getBytes().length);
                    r.getResponseBody().write(response.toString().getBytes());
                    r.getResponseBody().close();
                    return;
                } else {
                    statusCode = 404;  // Not Found
                }

                tx.success();
            } catch (Exception e) {
                e.printStackTrace();
                statusCode = 500;  // Internal Server Error
            }
        }

        if (statusCode != 200) {
            r.sendResponseHeaders(statusCode, -1);
            r.getResponseBody().close();
        }
    }
}
