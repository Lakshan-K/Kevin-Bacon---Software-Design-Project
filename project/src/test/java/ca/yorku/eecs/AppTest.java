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
                byte[] input = jsonString.getBytes("UTF-8");
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
                byte[] input = jsonString.getBytes("UTF-8");
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
                byte[] input = jsonString.getBytes("UTF-8");
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
                byte[] input = jsonString.getBytes("UTF-8");
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
                byte[] input = jsonString.getBytes("UTF-8");
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
                byte[] input = jsonString.getBytes("UTF-8");
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
                byte[] input = jsonString.getBytes("UTF-8");
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
                byte[] input = jsonString.getBytes("UTF-8");
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
                byte[] input = jsonString.getBytes("UTF-8");
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
                byte[] input = jsonString.getBytes("UTF-8");
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
                byte[] input = jsonString.getBytes("UTF-8");
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
                byte[] input = jsonString.getBytes("UTF-8");
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
                byte[] input = jsonString.getBytes("UTF-8");
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
            String duplicateJsonString = "{\"name\":\"Oscars\",\"awardId\":\"ad0000001\",\"actorId\":\"nm1001213\"}";
            URL duplicateUrl = new URL("http://localhost:8080/api/v1/addAward");
            HttpURLConnection duplicateConnection = (HttpURLConnection) duplicateUrl.openConnection();
            duplicateConnection.setRequestMethod("PUT");
            duplicateConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            duplicateConnection.setDoOutput(true);
            try (OutputStream os = duplicateConnection.getOutputStream()) {
                byte[] input = duplicateJsonString.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }
            int duplicateResponseCode = duplicateConnection.getResponseCode();
            assertEquals(400, duplicateResponseCode);
            duplicateConnection.disconnect();

        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test GET Award Pass
     */
    public void testGetAwardPass(){
        try {
            String jsonString = "{\"name\":\"Oscars\"}";

            // Encode JSON string for URL
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

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

    /**
     * Test GET Award Fail
     */
    public void testGetAwardFail(){
        try {
            String jsonString = "{\"name\":\"Best Director\"}";

            // Encode JSON string for URL
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/getAward?jsonString=" + encodedJson);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(404, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // test for GetActorPass
    public void testGetActorPass() {
        try {
            // Step 1: Add an actor to the system
            String addActorJsonString = "{\"name\":\"Test Actor\",\"actorId\":\"nm2000213\"}";
            URL addActorURL = new URL("http://localhost:8080/api/v1/addActor");
            HttpURLConnection addActorConnection = (HttpURLConnection) addActorURL.openConnection();
            addActorConnection.setRequestMethod("PUT");
            addActorConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            addActorConnection.setDoOutput(true);
            try (OutputStream os = addActorConnection.getOutputStream()) {
                byte[] input = addActorJsonString.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }
            int addActorResponseCode = addActorConnection.getResponseCode();
            assertEquals(200, addActorResponseCode);
            addActorConnection.disconnect();

            // Adding a small delay to ensure the actor is fully added
            Thread.sleep(500);

            // Step 2: Encode JSON for GET request similar to getAwardPass
            String jsonString = "{\"actorId\":\"nm2000213\"}";
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            // Step 3: Try to retrieve the actor using GET
            URL getActorURL = new URL("http://localhost:8080/api/v1/getActor?jsonString=" + encodedJson);
            HttpURLConnection getActorConnection = (HttpURLConnection) getActorURL.openConnection();
            getActorConnection.setRequestMethod("GET");
            getActorConnection.setRequestProperty("Content-Type", "application/json; utf-8");

            // Get response code
            int getActorResponseCode = getActorConnection.getResponseCode();
            assertEquals(200, getActorResponseCode);

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(getActorConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Log the response for debugging
            System.out.println("Response: " + response.toString());

            // Assert that the response body contains the actor's name
            String responseBody = response.toString();
            assertTrue(responseBody.contains("Test Actor"));

            getActorConnection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for GET Get Actor Fail
     */
    public void testGetActorFail() {
        try {
            // Try to retrieve a non-existent actor using GET
            String getActorJsonString = "{\"actorId\":\"nm9999999\"}";
            URL getActorURL = new URL("http://localhost:8080/api/v1/getActor");
            HttpURLConnection getActorConnection = (HttpURLConnection) getActorURL.openConnection();
            getActorConnection.setRequestMethod("GET");
            getActorConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            getActorConnection.setDoOutput(true);
            try (OutputStream os = getActorConnection.getOutputStream()) {
                byte[] input = getActorJsonString.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }
            int getActorResponseCode = getActorConnection.getResponseCode();
            assertEquals(404, getActorResponseCode);

            getActorConnection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for GET Get Movie Pass
     */
    public void testGetMoviePass() {
        try {
            // Step 1: Add a movie to the system
            String addMovieJsonString = "{\"name\":\"Inception\",\"movieId\":\"nm1234567\"}";
            URL addMovieURL = new URL("http://localhost:8080/api/v1/addMovie");
            HttpURLConnection addMovieConnection = (HttpURLConnection) addMovieURL.openConnection();
            addMovieConnection.setRequestMethod("PUT");
            addMovieConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            addMovieConnection.setDoOutput(true);
            try (OutputStream os = addMovieConnection.getOutputStream()) {
                byte[] input = addMovieJsonString.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }
            int addMovieResponseCode = addMovieConnection.getResponseCode();
            assertEquals(200, addMovieResponseCode);
            addMovieConnection.disconnect();

            // Adding a small delay to ensure the movie is fully added
            Thread.sleep(500);

            // Step 2: Encode JSON for GET request similar to getActorPass
            String jsonString = "{\"movieId\":\"nm1234567\"}";
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            // Step 3: Try to retrieve the movie using GET
            URL getMovieURL = new URL("http://localhost:8080/api/v1/getMovie?jsonString=" + encodedJson);
            HttpURLConnection getMovieConnection = (HttpURLConnection) getMovieURL.openConnection();
            getMovieConnection.setRequestMethod("GET");
            getMovieConnection.setRequestProperty("Content-Type", "application/json; utf-8");

            // Get response code
            int getMovieResponseCode = getMovieConnection.getResponseCode();
            assertEquals(200, getMovieResponseCode);

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(getMovieConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Log the response for debugging
            System.out.println("Response: " + response.toString());

            // Assert that the response body contains the movie's name
            String responseBody = response.toString();
            assertTrue(responseBody.contains("Inception"));

            getMovieConnection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test case for GET Get Movie Fail
     */
    public void testGetMovieFail() {
        try {
            // Try to retrieve a non-existent movie using GET
            String getMovieJsonString = "{\"movieId\":\"nm9999999\"}";
            String encodedJson = URLEncoder.encode(getMovieJsonString, "UTF-8");

            URL getMovieURL = new URL("http://localhost:8080/api/v1/getMovie?jsonString=" + encodedJson);
            HttpURLConnection getMovieConnection = (HttpURLConnection) getMovieURL.openConnection();
            getMovieConnection.setRequestMethod("GET");
            getMovieConnection.setRequestProperty("Content-Type", "application/json; utf-8");

            // Get response code
            int getMovieResponseCode = getMovieConnection.getResponseCode();
            assertEquals(404, getMovieResponseCode);

            getMovieConnection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            fail("Exception occurred: " + e.getMessage());
        }
    }
}
