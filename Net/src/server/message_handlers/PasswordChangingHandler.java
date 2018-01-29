package server.message_handlers;

import server.Account;
import server.Authenticator;

import java.util.Objects;

public class PasswordChangingHandler implements SocketHandler {
    private Authenticator authenticator;
    private static final String pswChangeWord = "::psw";

    public PasswordChangingHandler(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public boolean handle(String message, Account account) {
        if (!Objects.equals(message, pswChangeWord))
            return false;
        authenticator.passwordChange(account);
        return true;
    }
}
