package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpReceiver implements Receiver {
    private static final int repeatNumber = 3;
    private ServerSocket serverSocket;

    TcpReceiver(int port) throws IOException {
        int i = repeatNumber;
        while (i > 0) {
            i--;
            try {
                this.serverSocket = new ServerSocket(port);
                return;
            } catch (IOException e) {
                System.out.printf("Can't run server on port%d\n", port);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                if (i == 0)
                    throw e;
            }
        }
    }


    @Override
    public Socket acceptNew() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Connection reset");
            return null;
        }

    }

    @Override
    public String read(Socket socket) {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            return dataInputStream.readUTF();
        } catch (IOException e) {
            System.out.printf("Connection aborted: %s:%s\n", socket.getInetAddress(), socket.getPort());
            return null;
        }
    }

    @Override
    public DataInputStream getInputStream(Socket socket) {
        try {
            return new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.printf("Connection aborted: %s:%s\n", socket.getInetAddress(), socket.getPort());
            return null;
        }
    }
}
