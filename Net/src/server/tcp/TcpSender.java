package server.tcp;

import server.Account;
import server.CommonSender;
import server.SocketWrapper;

import java.io.IOException;

public class TcpSender extends CommonSender {
    @Override
    public void send(String message, Account sender, SocketWrapper receiver) {
        try {
            String senderName = sender == null ? "Server" : sender.getLogin();
            receiver.write(String.format("%s: %s", senderName, message));
        } catch (IOException e) {
            System.out.printf("Outgoing connection aborted: %s\n", receiver.getAddress());
        }
    }
}
