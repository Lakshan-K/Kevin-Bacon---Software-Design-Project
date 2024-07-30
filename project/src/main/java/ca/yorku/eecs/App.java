package ca.yorku.eecs;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class App 
{
    static int PORT = 8080;
    public static void main(String[] args) throws IOException
    {
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", PORT), 0);

        server.createContext("/api/v1/addActor", new AddActor());
        server.createContext("/api/v1/addMovie", new AddMovie());
        server.createContext("/api/v1/addDirector", new AddDirector());
        server.createContext("/api/v1/addAward", new AddAward());
        server.createContext("/api/v1/addRelationship", new AddRelationship());
        server.createContext("/api/v1/addCoActorRelationship", new AddCoActorRelationship());
        server.createContext("/api/v1/addDirectorRelationship", new AddDirectorRelationship());
        server.createContext("/api/v1/addAwardRelationship", new AddAwardRelationship());
        server.start();
        System.out.printf("Server started on port %d...\n", PORT);
    }
}
