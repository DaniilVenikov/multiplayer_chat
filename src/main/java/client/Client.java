package client;

import configuration.FileSystemServerConfigurator;
import configuration.ServerConfiguration;
import configuration.ServerConfigurator;
import messageUtils.Message;
import messageUtils.MessageImpl;
import server.Server;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите свой никнейм:");
        String nick = scanner.nextLine();
        System.out.println("Идёт подключение к чату (для выхода из чата введите \"exit\")");

        while (true){
            try(Socket clientSocket = new Socket(host, port);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){

                System.out.println(nick + ": ");
                String text = scanner.nextLine();
                if(text.equals("exit")){
                    out.println(new MessageImpl(nick, "Пользователь покинул чат", Calendar.getInstance().getTime()));
                    writeClientInLog(in.readLine());
                    break;
                }
                Message message = new MessageImpl(nick, text, Calendar.getInstance().getTime());
                out.println(message);

                writeClientInLog(in.readLine());
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
