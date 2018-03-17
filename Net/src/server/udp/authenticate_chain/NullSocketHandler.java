package server.udp.authenticate_chain;

import server.Account;
import server.Sender;
import server.SocketWrapper;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class NullSocketHandler implements AuthenticateStep {

    private final ConcurrentHashMap<SocketAddress, Account> addressAccount;
    private final Sender sender;

    public NullSocketHandler(ConcurrentHashMap<SocketAddress, Account> addressAccount, Sender sender) {
        this.addressAccount = addressAccount;
        this.sender = sender;
    }

    @Override
    public boolean handle(Account account, SocketWrapper socket) {
        if (account.getSocket() != null)
            return false;
        addressAccount.put(socket.getAddress(), new Account());
        sender.send("Login:", null, socket);
        return true;
    }
}
