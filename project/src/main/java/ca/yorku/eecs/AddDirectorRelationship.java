package ca.yorku.eecs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

import java.io.IOException;

public class AddDirectorRelationship implements HttpHandler {
    public AddDirectorRelationship() {}

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

        // variables to hold the HTTP status code, and the ID of the director and the movie
        int statusCode = 0;
        String directorId = "";
        String movieId = "";

        // check if the required information is present in the body. If not, raise error 400
        if (deserialized.has("directorId"))
            directorId = deserialized.getString("directorId");
        else
            statusCode = 400;

        if (deserialized.has("movieId"))
            movieId = deserialized.getString("movieId");
        else
            statusCode = 400;

        try (Transaction tx = Utils.driver.session().beginTransaction()) {
            // check if there is any existing relationship between directorId and movieId
            StatementResult result = tx.run("MATCH (d:Director {directorId: $directorId})-[r:DIRECTED]->(m:Movie {movieId: $movieId}) " +
                    "RETURN r", org.neo4j.driver.v1.Values.parameters("directorId", directorId, "movieId", movieId));

            // check for duplicate entries
            if (result.hasNext()) {
                statusCode = 400;
            } else {
                // make the query
                tx.run("MATCH (d:DIRECTOR {directorId: $directorId}), (m:Movie {movieId: $movieId}) " +
                                "CREATE (d)-[r:DIRECTED]->(m)",
                        org.neo4j.driver.v1.Values.parameters("directorId", directorId, "movieId", movieId));

                // commit the query for persistence
                tx.success();

                System.out.println("Director Relation added");
                statusCode = 200;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            statusCode = 500;
        }

        r.sendResponseHeaders(statusCode, -1);
    }
}
