package server.udp.authenticate_chain;

import server.Account;
import server.Sender;
import server.SocketWrapper;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class GratitudeHandler implements AuthenticateStep {
    private final ConcurrentHashMap<SocketAddress, Account> addressAccount;
    private Sender sender;

    public GratitudeHandler(ConcurrentHashMap<SocketAddress, Account> addressAccount,
                            Sender sender) {
        this.addressAccount = addressAccount;
        this.sender = sender;
    }

    @Override
    public boolean handle(Account account, SocketWrapper socket) {
        if (account != null)
            return false;
        account = new Account();
        addressAccount.put(socket.getAddress(), account);
        sender.send("Login:", null, socket);
        return true;
    }
}
