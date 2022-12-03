package Server;

import Server.Server;
import messageUtils.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private Server server;
    private Message outMessage; // исходящее сообщение
    private Scanner inMessage;
    private static String HOST;
    private static int PORT;

    private Socket clientSocket = null;

    private static int clientCount = 0;

    public ClientHandler(Socket clientSocket, Server server){
        try(Scanner fileReader =
                    new Scanner("/Users/daniil/IdeaProjects/chat/src/main/resources/settings.tx")) {
            clientCount++;
            this.clientSocket = clientSocket;
            this.server = server;
            HOST = fileReader.next();
            PORT = fileReader.nextInt();
        }
    }


    @Override
    public void run() {
        try{
           while (true){
               server.sendMessageToAllClients("Новый участник вошёл чат");
               server.sendMessageToAllClients("Клиентов в чате: " + clientCount);
               break;
           }

           while (true){
               if (inMessage.hasNext()){
                   String clientMessage = inMessage.nextLine();
                   if(clientMessage.equalsIgnoreCase("exit")) break;

                   System.out.println(clientMessage);

                   server.sendMessageToAllClients(clientMessage);
               }

               Thread.sleep(100);
           }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            this.close();
        }
    }


    //TODO реализовать метод
    // отправляем сообщение
    public void sendMsg(String msg){

    }

    public void close(){
        server.removeClient(this);
        clientCount--;

    }
}
