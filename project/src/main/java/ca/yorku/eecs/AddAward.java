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
            e.printStackTrace();
        }
    }

    public void handlePut(HttpExchange r) throws IOException, JSONException {
        // convert the request body
        String body = Utils.convert(r.getRequestBody());

        // get the deserialized JSON
        JSONObject deserialized = new JSONObject(body);

        // variables to hold the HTTP status code, the name and awardId of the award
        int statusCode = 0;
        String awardName = "";
        String awardId = "";

        // check if the required information is present in the body. If not, raise error 400
        if (deserialized.has("name"))
            awardName = deserialized.getString("name");
        else
            statusCode = 400;

        if (deserialized.has("awardId"))
            awardId = deserialized.getString("awardId");
        else
            statusCode = 400;

        try (Transaction tx = Utils.driver.session().beginTransaction()) {
            // check if there is any data with the same awardId
            StatementResult result = tx.run("MATCH (w:Award {awardId: $awardId}) RETURN w",
                    org.neo4j.driver.v1.Values.parameters("awardId", awardId));

            // check for duplicate entries
            if (result.hasNext()) {
                statusCode = 400;
            } else {
                // make the query
                tx.run("CREATE (w:Award {name: $awardName, awardId: $awardId})",
                        org.neo4j.driver.v1.Values.parameters("awardName", awardName, "awardId", awardId));

                // commit the query for persistence
                tx.success();

                System.out.println("Award added: " + awardName);
                statusCode = 200;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            statusCode = 500;
        }

        r.sendResponseHeaders(statusCode, -1);
    }
}
