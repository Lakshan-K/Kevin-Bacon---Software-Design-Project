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
        TestSuite suite = new TestSuite();
        suite.addTest(new AppTest("testSequence"));
        return suite;
    }

    public void testSequence() {
        bulkInsertData();

        testAddActorPass();
        testAddActorFail();
        testAddMoviePass();
        testAddMovieFail();
        testAddRelationshipPass();
        testAddRelationshipFail();
        testAddCoActorRelationshipPass();
        testAddCoActorRelationshipFail();
        testAddDirectorPass();
        testAddDirectorFail();
        testAddDirectorRelationshipPass();
        testAddDirectorRelationshipFail();
        testAddAwardPass();
        testAddAwardFail();
        testGetAwardPass();
        testGetAwardFail();
        testGetActorPass();
        testGetActorFail();
        testGetMoviePass();
        testGetMovieFail();
        testGetCoActorPass();
        testGetCoActorFail();
        testGetDirectorPass();
        testGetDirectorFail();
        testHasRelationshipPass();
        testHasRelationshipFail();
        testComputeBaconNumberPass();
        testComputeBaconNumberFail();
        testComputeBaconPathPass();
        testComputeBaconPathFail();
    }

    /**
     * Helper method to bulk add the data for compute bacon number and compute bacon path
     */
    private void bulkInsertData() {

        // 2D array for all actors
        String[][] actors = {
                {"nm0000102", "Kevin Bacon"},
                {"nm0000134", "Tom Hanks"},
                {"nm0000123", "Robert De Niro"},
                {"nm0000173", "Morgan Freeman"},
                {"nm0000209", "Al Pacino"},
                {"nm0000375", "Robert Downey Jr."}
        };

        // 2D array for all movies
        String[][] movies = {
                {"tt0097576", "Indiana Jones and the Last Crusade"},
                {"tt0110912", "Pulp Fiction"},
                {"tt0107290", "Jurassic Park"},
                {"tt0111161", "The Shawshank Redemption"},
                {"tt0137523", "Fight Club"},
                {"tt0848228", "The Avengers"}
        };

        // 2D array for all relationships
        String[][] relationships = {
                {"nm0000102", "tt0097576"},
                {"nm0000134", "tt0097576"},
                {"nm0000173", "tt0111161"},
                {"nm0000209", "tt0110912"},
                {"nm0000123", "tt0110912"},
                {"nm0000375", "tt0848228"}
        };

        // 2D array for coActor relationships
        String[][] coActors = {
                {"nm0000102", "nm0000134"},
                {"nm0000102", "nm0000123"},
                {"nm0000102", "nm0000173"},
                {"nm0000102", "nm0000209"},
                {"nm0000102", "nm0000375"},
                {"nm0000134", "nm0000123"},
                {"nm0000134", "nm0000173"},
                {"nm0000134", "nm0000209"},
                {"nm0000134", "nm0000375"},
                {"nm0000123", "nm0000173"}
        };

        // add the actors to the database
        for(String[] actor : actors) {
            try {
                String jsonString = "{\"name\":\"" + actor[1] + "\",\"actorId\":\"" + actor[0] + "\"}";

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

                assertEquals(200, connection.getResponseCode());

                connection.disconnect();
            } catch (Exception e) {
                System.out.println(e);
                fail("Exception occurred: " + e.getMessage());
            }
        }

        // add the movies to the database
        for(String[] movie : movies) {
            try {
                String jsonString = "{\"name\":\"" + movie[1] + "\",\"movieId\":\"" + movie[0] + "\"}";

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

                assertEquals(200, connection.getResponseCode());

                connection.disconnect();
            } catch (Exception e) {
                System.out.println(e);
                fail("Exception occurred: " + e.getMessage());
            }
        }

        // add the relationships to the database
        for(String[] relationship : relationships) {
            try {
                String jsonString = "{\"actorId\":\"" + relationship[0] + "\",\"movieId\":\"" + relationship[1] + "\"}";

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

                assertEquals(200, connection.getResponseCode());

                connection.disconnect();
            } catch (Exception e) {
                System.out.println(e);
                fail("Exception occurred: " + e.getMessage());
            }
        }

        // add the co-actors to the database
        for(String[] coActor : coActors) {
            try {
                String jsonString = "{\"actorId\":\"" + coActor[0] + "\",\"coActorId\":\"" + coActor[1] + "\"}";

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

                assertEquals(200, connection.getResponseCode());

                connection.disconnect();
            } catch (Exception e) {
                System.out.println(e);
                fail("Exception occurred: " + e.getMessage());
            }
        }
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
            // add the co actor
            String jsonString = "{\"name\":\"Dwayne Johnson\",\"actorId\":\"nm1001231\"}";

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

            // check 200 response code
            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            jsonString = "{\"actorId\":\"nm1001231\",\"coActorId\":\"nm1001213\"}";

            // Create URL object
            getURL = new URL("http://localhost:8080/api/v1/addCoActorRelationship");

            // Open connection
            connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            // Get response code
            responseCode = connection.getResponseCode();
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

            int responseCode = connection.getResponseCode();
            assertEquals(400, responseCode);

            connection.disconnect();

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
            String jsonString = "{\"actorId\":\"nm1001213\"}";
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
            assertNotNull(responseBody);

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
            String jsonString = "{\"movieId\":\"nm7001453\"}";
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
            assertNotNull(responseBody);

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

    /**
     * VARUN MALHOTRA
     * Test case for GET Director Pass
     */
    public void testGetDirectorPass() {
        try {
            String jsonString = "{\"directorId\":\"nm1002101\"}";
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            URL url = new URL("http://localhost:8080/api/v1/getDirector?jsonString=" + encodedJson);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
     * VARUN MALHOTRA
     * Test case for Get Director Fail
     */
    public void testGetDirectorFail() {
        try {
            String jsonString = "{\"directorId\":\"nm1002111\"}";
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            URL url = new URL("http://localhost:8080/api/v1/getDirector?jsonString=" + encodedJson);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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

    /**
     * VARUN MALHOTRA
     * Test case for GET CoActor Pass
     */
    public void testGetCoActorPass() {
        try {
            String jsonString = "{\"coActorId\":\"nm1001213\"}";
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            URL url = new URL("http://localhost:8080/api/v1/getCoActor?jsonString=" + encodedJson);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
     * VARUN MALHOTRA
     * Test case for Get CoActor Fail
     */
    public void testGetCoActorFail() {
        try {
            String jsonString = "{\"coActorId\":\"nm1001244\"}";
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            URL url = new URL("http://localhost:8080/api/v1/getCoActor?jsonString=" + encodedJson);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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

    /**
     * VARUN MALHOTRA
     * Test case for Has Relationship Pass
     */
    public void testHasRelationshipPass() {
        try {
            String jsonString = "{\"actorId\":\"nm1001213\",\"movieId\":\"nm7001453\"}";
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            URL url = new URL("http://localhost:8080/api/v1/hasRelationship?jsonString=" + encodedJson);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
     * VARUN MALHOTRA
     * Test case for Has Relationship Fail
     */
    public void testHasRelationshipFail() {
        try {
            String jsonString = "{\"actorId\":\"nm1001231\",\"movieId\":\"nm7001453\"}";
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            URL url = new URL("http://localhost:8080/api/v1/hasRelationship?jsonString=" + encodedJson);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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

    /**
     * Test GET Compute Bacon Number Pass
     */
    public void testComputeBaconNumberPass() {
        try {
            String jsonString = "{\"actorId\":\"nm0000102\"}"; // Kevin Bacon's actorId

            // Encode JSON string for URL
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/computeBaconNumber?jsonString=" + encodedJson);

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
            assertTrue(responseBody.contains("\"baconNumber\":0")); // Assuming Kevin Bacon has a Bacon Number of 0

            connection.disconnect();
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test GET Compute Bacon Number Fail
     */
    public void testComputeBaconNumberFail() {
        try {
            String jsonString = "{\"actorId\":\"nonexistentActorId\"}"; // Invalid actorId

            // Encode JSON string for URL
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/computeBaconNumber?jsonString=" + encodedJson);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(404, responseCode); // Expecting 404 Not Found for a nonexistent actor

            connection.disconnect();
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test GET Compute Bacon Path Pass
     */
    public void testComputeBaconPathPass() {
        try {
            String jsonString = "{\"actorId\":\"nm0000375\"}"; // Robert Downey Jr.'s actorId

            // Encode JSON string for URL
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/computeBaconPath?jsonString=" + encodedJson);

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
            assertTrue(responseBody.contains("\"nm0000102\"")); // Path should end with Kevin Bacon's actorId

            connection.disconnect();
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * Test GET Compute Bacon Path Fail
     */
    public void testComputeBaconPathFail() {
        try {
            String jsonString = "{\"actorId\":\"nonexistentActorId\"}"; // Invalid actorId

            // Encode JSON string for URL
            String encodedJson = URLEncoder.encode(jsonString, "UTF-8");

            // Create URL object
            URL getURL = new URL("http://localhost:8080/api/v1/computeBaconPath?jsonString=" + encodedJson);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");

            // Get response code
            int responseCode = connection.getResponseCode();
            assertEquals(404, responseCode); // Expecting 404 Not Found for a nonexistent actor

            connection.disconnect();
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}
