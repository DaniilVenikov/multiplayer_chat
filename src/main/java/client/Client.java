package client;

import configuration.FileSystemServerConfigurator;
import configuration.ServerConfiguration;
import configuration.ServerConfigurator;
import server.Server;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;

public class Client implements Runnable{


    private ServerConfiguration readConfiguration(){
        ServerConfigurator serverConfigurator = new FileSystemServerConfigurator();
        return serverConfigurator
                .loadConfiguration(URI.create("file:/Users/daniil/IdeaProjects/chat/src/main/resources/settings.txt"));
    }


    @Override
    public void run() {
        String host = readConfiguration().getHost();
        int port = readConfiguration().getPort();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите свой никнейм:");
        try {
            String nick = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            try(Socket clientSocket = new Socket(host, port);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeClientInLog(String text){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("File.log", true))){
            writer.write(text);
            writer.write('\n');
            writer.flush();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
