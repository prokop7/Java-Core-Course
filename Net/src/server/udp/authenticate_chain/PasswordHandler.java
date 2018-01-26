package server.udp.authenticate_chain;

import server.Account;
import server.Sender;
import server.SocketWrapper;

import java.util.concurrent.ConcurrentHashMap;

public class PasswordHandler implements AuthenticateStep {
    private final ConcurrentHashMap<String, Account> loginPassword;
    private final Sender sender;

    public PasswordHandler(ConcurrentHashMap<String, Account> loginPassword, Sender sender) {
        this.loginPassword = loginPassword;
        this.sender = sender;
    }

    @Override
    public boolean handle(Account account, SocketWrapper socket) {
        if (account.getPassword() != null)
            return false;
        String psw = socket.read();
        Account cachedAccount = loginPassword.get(account.getLogin());
        if (cachedAccount.getPassword() != null) {
            if (!cachedAccount.getPassword().equals(psw)) {
                sender.send("Wrong password, try again:", null, socket);
                return true;
            }
        }
        account.setPassword(psw);
        account.setSocket(socket);
        account.updateActive();
        loginPassword.put(account.getLogin(), account);
        sender.subscribe(socket);
        sender.send("Welcome " + account.getLogin(), null);
        return true;
    }
}
