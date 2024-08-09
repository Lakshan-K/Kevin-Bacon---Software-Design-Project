package ca.yorku.eecs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.summary.ResultSummary;

import java.io.IOException;

public class AddRelationship implements HttpHandler {
    public AddRelationship(){}

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

        // variables to hold the HTTP status code and the ID for actor and movie
        int statusCode = 0;
        String actorId = "";
        String movieId = "";

        // check if the required information is present in the body. If not, raise error 400
        if (deserialized.has("actorId")) {
            actorId = deserialized.getString("actorId");

            // if actorId is empty, raise an error
            if (actorId.isEmpty()) {
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

                StatementResult result = tx.run("MATCH (a:Actor {actorId: $actorId})\n" +
                        "MATCH (m:Movie {movieId: $movieId})\n" +
                        "RETURN a, m", org.neo4j.driver.v1.Values.parameters("actorId", actorId, "movieId", movieId));

                // check if the result has value which indicates that the actorID and movieID exists.
                // if empty, then either of them or both do no exist
                if (!result.hasNext()) {
                    statusCode = 404;
                } else {
                    // check if there is any existing relationship between actorId and movieId
                    result = tx.run("MATCH (a:Actor {actorId: $actorId})-[r:ACTED_IN]->(m:Movie {movieId: $movieId}) " +
                            "RETURN r", org.neo4j.driver.v1.Values.parameters("actorId", actorId, "movieId", movieId));

                    // check for duplicate entries
                    if (result.hasNext()) {
                        statusCode = 400;
                    } else {
                        // make the query
                        tx.run("MATCH (a:Actor {actorId: $actorId}), (m:Movie {movieId: $movieId}) " +
                                        "CREATE (a)-[r:ACTED_IN]->(m)",
                                org.neo4j.driver.v1.Values.parameters("actorId", actorId, "movieId", movieId));

                        // commit the query for persistence
                        tx.success();

                        System.out.println("Relation added");
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
