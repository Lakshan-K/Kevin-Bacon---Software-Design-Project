package ca.yorku.eecs;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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

    /**
     * Test case for PUT Add Actor Pass
     */
    public void testAddActorPass() {
        try {
            String jsonString = "{\"name\":\"Denzel Washington\",\"actorId\":\"nm1001213\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addActor");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add Actor Fail
     */
    public void testAddActorFail() {
        try {
            String jsonString = "{\"name\":\"Robert Downey Jr.\",\"actorId\":\"nm1001213\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addActor");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(400, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add Movie Pass
     */
    public void testAddMoviePass() {
        try {
            String jsonString = "{\"name\":\"Parasite\",\"movieId\":\"nm7001453\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addMovie");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add Movie Fail
     */
    public void testAddMovieFail() {
        try {
            String jsonString = "{\"name\":\"Iron Man\",\"movieId\":\"nm7001453\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addMovie");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(400, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add Relationship Pass
     */
    public void testAddRelationshipPass() {
        try {
            String jsonString = "{\"actorId\":\"nm1001213\",\"movieId\":\"nm7001453\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addRelationship");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add Relationship Fail
     */
    public void testAddRelationshipFail() {
        try {
            String jsonString = "{\"actorId\":\"nm1001213\",\"movieId\":\"nm7001453\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addRelationship");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(400, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add CoActor Relationship Pass
     */
    public void testAddCoActorRelationshipPass() {
        try {
            String jsonString = "{\"actorId\":\"nm1001231\",\"coActorId\":\"nm1001213\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addCoActorRelationship");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add CoActor Relationship Fail
     */
    public void testAddCoActorRelationshipFail() {
        try {
            String jsonString = "{\"actorId\":\"nm1001231\",\"coActorId\":\"nm1001213\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addCoActorRelationship");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(400, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add Director Pass
     */
    public void testAddDirectorPass() {
        try {
            String jsonString = "{\"name\":\"Anthony Russo\",\"directorId\":\"nm1002101\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addDirector");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add Director Fail
     */
    public void testAddDirectorFail() {
        try {
            String jsonString = "{\"name\":\"Joseph Russo\",\"directorId\":\"nm1002101\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addDirector");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(400, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add Director Relationship Pass
     */
    public void testAddDirectorRelationshipPass() {
        try {
            String jsonString = "{\"directorId\":\"nm1002101\",\"movieId\":\"nm7001453\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addDirectorRelationship");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add Director Relationship Fail
     */
    public void testAddDirectorRelationshipFail() {
        try {
            String jsonString = "{\"directorId\":\"nm1002101\",\"movieId\":\"nm7001453\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addDirectorRelationship");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(400, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add Award and Award Relationship Pass
     */
    public void testAddAwardPass() {
        try {
            String jsonString = "{\"name\":\"Oscars\",\"awardId\":\"ad0000001\",\"actorId\":\"nm1001213\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addAward");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for PUT Add Award and Award Relationship Fail
     */
    public void testAddAwardFail() {
        try {
            String jsonString = "{\"name\":\"Oscars\",\"awardId\":\"ad0000001\",\"actorId\":\"nm1001213\"}";

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/addAward");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(400, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
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
