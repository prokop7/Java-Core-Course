package server.tcp;

import server.Account;
import server.Sender;
import server.SocketWrapper;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class CommonSender implements Sender {
    private ConcurrentHashMap.KeySetView<SocketWrapper, Boolean> sockets = ConcurrentHashMap.newKeySet();

    @Override
    protected void finalize() throws Throwable {
        sockets.clear();
        super.finalize();
    }

    @Override
    public void subscribe(SocketWrapper socket) {
        sockets.add(socket);
    }

    @Override
    public void unsubscribe(SocketWrapper socket) {
        sockets.remove(socket);
    }

    @Override
    public void send(String message, Account sender) {
        for (SocketWrapper receiver : sockets) {
            if (sender == null || !receiver.equals(sender.getSocket()))
                send(message, sender, receiver);
        }
    }

    @Override
    public void send(String message, Account sender, SocketWrapper receiver) {
        try {
            String senderName = sender == null ? "Server" : sender.getLogin();
            receiver.write(String.format("%s: %s", senderName, message));
        } catch (IOException e) {
            System.out.printf("Outgoing connection aborted: %s\n", receiver.getAddress());
        }
    }

    @Override
    public void send(String message, Account sender, int receiverPort) {
        SocketWrapper receiver = null;
        for (SocketWrapper socket : sockets) {
            if (socket.getPort() == receiverPort) {
                receiver = socket;
                break;
            }
        }
        if (receiver == null) {
            System.out.println("No user with such port");
            return;
        }

        try {
            String senderName = sender == null ? "Server" : sender.getLogin();
            receiver.write(String.format("%s: %s", senderName, message));
        } catch (IOException e) {
            System.out.printf("Outgoing connection aborted: %s\n", receiver.getAddress());
        }
    }
}
