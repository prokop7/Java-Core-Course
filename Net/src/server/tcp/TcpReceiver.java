package server.tcp;

import server.Receiver;
import server.SocketWrapper;

import java.io.IOException;
import java.net.ServerSocket;

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
    public SocketWrapper acceptNew() {
        try {
            return new TcpSocket(serverSocket.accept());
        } catch (IOException e) {
            System.out.println("Connection reset");
            return null;
        }
    }
}
