package server;

import configuration.FileSystemServerConfigurator;
import configuration.ServerConfiguration;
import configuration.ServerConfigurator;
import connectionUtils.Connection;
import connectionUtils.ConnectionImpl;
import connectionUtils.ConnectionListener;
import messageUtils.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class Server implements ConnectionListener {


    private final int PORT;

    ServerSocket serverSocket;

    private Set<Connection> connections;

    public Server(){
        ServerConfigurator serverConfigurator = new FileSystemServerConfigurator();
        ServerConfiguration configuration =
                serverConfigurator.loadConfiguration(URI.create("file:/Users/daniil/IdeaProjects/chat/src/main/resources/settings.txt"));

        PORT = configuration.getPort();
        connections = new HashSet<>();
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void startServer(){
        System.out.println("Server started");
        while (true){
            try(Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                connectionCreated(new ConnectionImpl(clientSocket, this));


            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void connectionCreated(Connection c) {
        connections.add(c);
        System.out.println("Connections was added");
    }

    @Override
    public synchronized void connectionClosed(Connection c) {
        connections.remove(c);
        c.close();
        System.out.println("Connections was closed");
    }

    @Override
    public synchronized void connectionException(Connection c, Exception e) {
        connections.remove(c);
        c.close();
        System.out.println("Connections was closed");
        e.printStackTrace();
    }

    @Override
    public synchronized void sendMessage(Message message) {
        for(Connection c : connections){
            c.send(message);
        }
    }
}
