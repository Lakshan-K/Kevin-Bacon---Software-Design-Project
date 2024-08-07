package ca.yorku.eecs;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class App {
    static int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", PORT), 0);

        // Registering the context for AddActor handler
        server.createContext("/api/v1/addActor", new AddActor());

        // Registering the context for AddMovie handler
        server.createContext("/api/v1/addMovie", new AddMovie());

        // Registering the context for AddDirector handler
        server.createContext("/api/v1/addDirector", new AddDirector());

        // Registering the context for AddAward handler
        server.createContext("/api/v1/addAward", new AddAward());

        // Registering the context for AddRelationship handler
        server.createContext("/api/v1/addRelationship", new AddRelationship());

        // Registering the context for AddCoActorRelationship handler
        server.createContext("/api/v1/addCoActorRelationship", new AddCoActorRelationship());

        // Registering the context for AddDirectorRelationship handler
        server.createContext("/api/v1/addDirectorRelationship", new AddDirectorRelationship());

        // Registering the context for AddAwardRelationship handler
        server.createContext("/api/v1/addAwardRelationship", new AddAwardRelationship());

        // Registering the context for GetActor handler
        server.createContext("/api/v1/getActor", new GetActor());

        // Starting the server
        server.start();
        System.out.printf("Server started on port %d...\n", PORT);
    }
}
