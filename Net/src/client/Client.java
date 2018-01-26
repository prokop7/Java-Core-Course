package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final int repeatNumber = 20;
    private static final int timeout = 1000;

    public static void main(String[] args) {
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        boolean stopped = false;
        while (!stopped) {
            Socket socket = establishConnection("127.0.0.1", 8080);
            if (socket == null)
                return;

            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                System.out.println("Can't open input/output streams");
                return;
            }
            SendTask sendTask = new SendTask(dataOutputStream);
            Thread sendThread = new Thread(sendTask);
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
                        while (sendThread.isAlive() || receiveThread.isAlive()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ignored) {
                            }
                        }
                        break;
                    }
                }
                stopped = sendTask.isStopped();
                if (!stopped) {
                    System.out.println("Reconnecting");
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Socket establishConnection(String ipAddress, int port) {
        int remainedTries = repeatNumber;
        while (true) {
            try {
                return new Socket(ipAddress, port);
            } catch (IOException e) {
                System.out.println("Can't connect, wait to repeat.");
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException ignored) {
                }
                if (--remainedTries == 0) {
                    System.out.println("Can't establish connection.");
                    return null;
                }
            }
        }
    }

}