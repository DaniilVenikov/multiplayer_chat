package connectionUtils;

import messageUtils.Message;

import java.io.*;
import java.net.Socket;

public class ConnectionImpl implements Connection, Runnable{

    private Socket socket;
    private ConnectionListener connectionListener;
    private boolean needToRun = true;
    private OutputStream out;

    public ConnectionImpl(Socket socket, ConnectionListener connectionListener) {
        this.socket = socket;
        this.connectionListener = connectionListener;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void send(Message message) {
        try(ObjectOutputStream objOut = new ObjectOutputStream(out)){
            objOut.writeObject(out);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        needToRun = false;
    }

    @Override
    public void run() {
        while (needToRun){
            try {
                InputStream in = socket.getInputStream();
                int amount = in.available();
                if (amount != 0){
                    ObjectInputStream objIn = new ObjectInputStream(in);
                    Message message = (Message) objIn.readObject();
                    connectionListener.sendMessage(message);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
