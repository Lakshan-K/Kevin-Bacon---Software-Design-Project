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
import java.util.List;

public class ComputeBaconPath implements HttpHandler {

    // Kevin Bacon's actorId
    private static final String KEVIN_BACON_ID = "nm0000102";

    // Constructor for ComputeBaconPath handler
    public ComputeBaconPath() {
        System.out.println("ComputeBaconPath handler initialized");
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

        // check if the request body is empty
        if(body.isEmpty()) {
            String uri = r.getRequestURI().toString().split("\\?jsonString=")[1];
            body = URLDecoder.decode(uri, "UTF-8");
            System.out.println("Request URI: " + body);
        }

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
                String query = "MATCH (bacon:actor {id: $baconId}), (actor:actor {id: $actorId}), " +
                        "p = shortestPath((actor)-[:ACTED_WITH*1..10]-(bacon)) " +
                        "RETURN [n IN nodes(p) | n.id] AS baconPath";

                StatementResult result = tx.run(query, org.neo4j.driver.v1.Values.parameters(
                        "actorId", actorId,
                        "baconId", KEVIN_BACON_ID
                ));

                if (result.hasNext()) {
                    Record record = result.next();
                    List<Object> path = record.get("baconPath").asList();

                    JSONArray baconPath = new JSONArray(path);

                    JSONObject response = new JSONObject();
                    response.put("baconPath", baconPath);

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

        r.sendResponseHeaders(statusCode, -1);
        r.getResponseBody().close();
    }
}
