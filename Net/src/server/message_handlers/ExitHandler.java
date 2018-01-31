package server.message_handlers;

import server.Account;
import server.Sender;

import java.util.Objects;

public class ExitHandler implements SocketHandler {
    private static final String exitWord = "::exit";
    private Sender sender;

    public ExitHandler(Sender sender) {
        this.sender = sender;
    }

    @Override
    public boolean handle(String message, Account account) {
        if (message != null && !Objects.equals(message, exitWord))
            return false;
        sender.send(String.format("%s has left chat", account.getLogin()), null);
        sender.unsubscribe(account);
        account.setSocket(null);
        return true;
    }
}
