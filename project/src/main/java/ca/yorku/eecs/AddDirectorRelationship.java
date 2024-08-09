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
            System.out.println(e.getMessage());
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
        if (deserialized.has("directorId")){
            directorId = deserialized.getString("directorId");

            // if movieId is empty, raise an error
            if (directorId.isEmpty()) {
                statusCode = 400;
            }
        } else
            statusCode = 400;

        if (deserialized.has("movieId")){
            movieId = deserialized.getString("movieId");

            // if movieId is empty, raise an error
            if (movieId.isEmpty()) {
                statusCode = 400;
            }
        } else
            statusCode = 400;

        if(statusCode == 0) {

            try (Transaction tx = Utils.driver.session().beginTransaction()) {

                StatementResult result = tx.run("MATCH (d:director {id: $directorId})\n" +
                        "MATCH (m:movie {id: $movieId})\n" +
                        "RETURN d, m", org.neo4j.driver.v1.Values.parameters("directorId", directorId, "movieId", movieId));

                // check if the result has value which indicates that the actorID and movieID exists.
                // if empty, then either of them or both do no exist
                if (!result.hasNext()) {
                    statusCode = 404;
                } else {
                    // check if there is any existing relationship between directorId and movieId
                    result = tx.run("MATCH (d:director {id: $directorId})-[r:DIRECTED]->(m:movie {id: $movieId}) " +
                            "RETURN r", org.neo4j.driver.v1.Values.parameters("directorId", directorId, "movieId", movieId));

                    // check for duplicate entries
                    if (result.hasNext()) {
                        statusCode = 400;
                    } else {
                        // make the query
                        tx.run("MATCH (d:director {id: $directorId}), (m:movie {id: $movieId}) " +
                                        "CREATE (d)-[r:DIRECTED]->(m)",
                                org.neo4j.driver.v1.Values.parameters("directorId", directorId, "movieId", movieId));

                        // commit the query for persistence
                        tx.success();

                        System.out.println("Director Relation added");
                        statusCode = 200;
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                statusCode = 500;
            }
        }

        r.sendResponseHeaders(statusCode, -1);
    }
}
