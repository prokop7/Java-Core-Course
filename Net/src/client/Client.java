package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket socket;
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        try {
            socket = new Socket("127.0.0.1", 8080);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            //TODO exception handle
            return;
        }

        Thread sendThread = new Thread(new SendTask(dataOutputStream));
        Thread receiveThread = new Thread(new ReceiveTask(dataInputStream));
        sendThread.start();
        receiveThread.start();
        try {
            while (true) {
                sendThread.join(100);
                receiveThread.join(100);
                if (!sendThread.isAlive() || !receiveThread.isAlive()) {
                    dataOutputStream.close();
                    dataInputStream.close();
                    socket.close();
                    return;
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

}