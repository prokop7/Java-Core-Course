package server.udp.authenticate_chain;

import server.Account;
import server.SocketWrapper;

import java.util.concurrent.ConcurrentHashMap;

public class ValidAccountHandler implements AuthenticateStep {
    private ConcurrentHashMap<String, Account> loginPassword;

    public ValidAccountHandler(ConcurrentHashMap<String, Account> loginPassword) {
        this.loginPassword = loginPassword;
    }

    @Override
    public boolean handle(Account account, SocketWrapper socket) {

        Account cachedAccount = loginPassword.get(account.getLogin());
        cachedAccount.updateActive();
        account.updateActive();
        cachedAccount.setSocket(socket);
        account.setSocket(socket);

        return false;
    }
}
