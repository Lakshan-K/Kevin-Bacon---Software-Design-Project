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

        // Registering the context for GetActor handler
        server.createContext("/api/v1/getActor", new GetActor());

        // Registering the context for GetMovie handler
        server.createContext("/api/v1/getMovie", new GetMovie());

        // Registering the context for GetCoActor handler
        server.createContext("/api/v1/getCoActor", new GetCoActor());

        // Registering the context for GetDirector handler
        server.createContext("/api/v1/getDirector", new GetDirector());

        // Registering the context for GetAward handler
        server.createContext("/api/v1/getAward", new GetAward());

        // Registering the context for HasRelationship handler
        server.createContext("/api/v1/HasRelationship", new HasRelationship());

        // Registering the context for ComputeBaconNumber handler
        server.createContext("/api/v1/computeBaconNumber", new ComputeBaconNumber());

        // Registering the context for ComputeBaconPath handler
        server.createContext("/api/v1/computeBaconPath", new ComputeBaconPath());

        // Starting the server
        server.start();
        System.out.printf("Server started on port %d...\n", PORT);
    }
}
