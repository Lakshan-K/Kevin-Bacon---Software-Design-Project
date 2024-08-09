package ca.yorku.eecs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    // test case for GET Award Pass
    public void testGetAwardPass(){
        try {
            String jsonString = "{\"name\":\"Oscars\"}";

            // Encode JSON string for URL
            String encodedJson = URLEncoder.encode(jsonString, StandardCharsets.UTF_8);

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/getAward?jsonString=" + encodedJson);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Assert response content
            String responseBody = response.toString();
            assertNotNull(responseBody);

            connection.disconnect();

        } catch (Exception e) {
           System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }
}
