package ca.yorku.eecs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

import java.io.IOException;

public class AddMovie implements HttpHandler {
    public AddMovie(){}

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

        // variables to hold the HTTP status code, the name and movieId of the movie
        int statusCode = 0;
        String movieName = "";
        String movieId = "";

        // check if the required information is present in the body. If not, raise error 400
        if (deserialized.has("name"))
            movieName = deserialized.getString("name");
        else
            statusCode = 400;

        if (deserialized.has("movieId"))
            movieId = deserialized.getString("movieId");
        else
            statusCode = 400;

        try (Transaction tx = Utils.driver.session().beginTransaction()) {
            // check if there is any data with the same actorId
            StatementResult result = tx.run("MATCH (m:movie {id: $movieId}) RETURN m",
                    org.neo4j.driver.v1.Values.parameters("movieId", movieId));

            // check for duplicate entries
            if (result.hasNext()) {
                statusCode = 400;
            } else {
                // make the query
                tx.run("CREATE (m:movie {Name:$name, id:$movieId})",
                        org.neo4j.driver.v1.Values.parameters("name", movieName, "movieId", movieId));

                // commit the query for persistence
                tx.success();

                System.out.println("Movie added: " + movieName);
                statusCode = 200;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            statusCode = 500;
        }

        r.sendResponseHeaders(statusCode, -1);
    }
}
