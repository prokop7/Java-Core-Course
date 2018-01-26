package server.udp.authenticate_chain;

import server.Account;
import server.Sender;
import server.SocketWrapper;

import java.net.SocketAddress;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class LoginHandler implements AuthenticateStep {
    private final ConcurrentHashMap<SocketAddress, Account> addressAccount;
    private final ConcurrentHashMap<String, Account> loginPassword;
    private final Sender sender;
    private long timeout;

    public LoginHandler(ConcurrentHashMap<SocketAddress, Account> addressAccount,
                        ConcurrentHashMap<String, Account> loginPassword,
                        Sender sender,
                        long timeout) {
        this.addressAccount = addressAccount;
        this.loginPassword = loginPassword;
        this.sender = sender;
        this.timeout = timeout;
    }

    @Override
    public boolean handle(Account account, SocketWrapper socket) {
        if (account.getLogin() != null)
            return false;
        String login = socket.read();
        Account cachedAccount = loginPassword.get(login);
        if (cachedAccount != null) {
            if (cachedAccount.getSocket() != null) {
                if (new Date().getTime() - cachedAccount.getLastAction() <= timeout) {
                    sender.send(
                            String.format("Account already in use until %s\nEnter login:",
                                    new Date(cachedAccount.getLastAction() + timeout)),
                            null,
                            socket);
                    return true;
                }
                sender.send("You have been disconnected due timeout", null, cachedAccount.getSocket());
                sender.unsubscribe(cachedAccount.getSocket());
                cachedAccount.setSocket(null);
            }
            account.setLogin(login);
            sender.send("Password:", null, socket);
            return true;
        }
        account.setLogin(login);
        loginPassword.put(login, account);
        sender.send("Enter your new password:", null, socket);
        return true;
    }
}
