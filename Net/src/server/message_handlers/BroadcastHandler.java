package server.message_handlers;

import server.Account;
import server.Sender;
import server.SocketHandler;

public class BroadcastHandler implements SocketHandler {
    private Sender sender;

    public BroadcastHandler(Sender sender) {
        this.sender = sender;
    }

    @Override
    public boolean handle(String message, Account account) {
        System.out.printf("%s: %s%n", account.getLogin(), message);
        sender.send(message, account);
        return false;
    }
}
