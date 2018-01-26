package server;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class CommonSender implements Sender {
    private ConcurrentHashMap.KeySetView<SocketWrapper, Boolean> sockets = ConcurrentHashMap.newKeySet();

    CommonSender() {
    }

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
            System.out.printf("Outgoing connection aborted: %s:%s\n", receiver.getInetAddress(), receiver.getPort());
        }
    }
}
