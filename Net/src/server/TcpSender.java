package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class TcpSender implements Sender {
    private ConcurrentHashMap.KeySetView<Socket, Boolean> sockets = ConcurrentHashMap.newKeySet();

    TcpSender() {
    }

    @Override
    protected void finalize() throws Throwable {
        sockets.clear();
        super.finalize();
    }

    @Override
    public void subscribe(Socket socket) {
        sockets.add(socket);
    }

    @Override
    public void unsubscribe(Socket socket) {
        sockets.remove(socket);
    }

    @Override
    public void send(String message, Account sender) {
        for (Socket receiver : sockets) {
            if (sender == null || !receiver.equals(sender.getSocket()))
                send(message, sender, receiver);
        }
    }

    @Override
    public void send(String message, Account sender, Socket receiver) {
        try {
            DataOutputStream outputStream = new DataOutputStream(receiver.getOutputStream());
            String senderName = sender == null ? "Server" : sender.getLogin();
            outputStream.writeUTF(String.format("%s: %s", senderName, message));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DataOutputStream getOutputStream(Socket socket) {
        try {
            return new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
