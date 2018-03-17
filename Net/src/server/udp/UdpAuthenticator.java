package server.udp;

import server.Account;
import server.Authenticator;
import server.Sender;
import server.SocketWrapper;
import server.udp.authenticate_chain.*;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UdpAuthenticator implements Authenticator {
    private ConcurrentHashMap<SocketAddress, Account> addressAccount = new ConcurrentHashMap<>();
    private List<AuthenticateStep> chain = new ArrayList<>();
    private Sender sender;


    UdpAuthenticator(Sender sender, long timeout) {
        ConcurrentHashMap<String, Account> loginPassword = new ConcurrentHashMap<>();
        chain.add(new GratitudeHandler(addressAccount, sender));
        chain.add(new LoginHandler(loginPassword, sender, timeout));
        chain.add(new PasswordHandler(loginPassword, sender));
        chain.add(new NullSocketHandler(addressAccount, sender));
        chain.add(new ValidAccountHandler(loginPassword));
    }

    UdpAuthenticator(Sender sender) {
        this(sender, 60_000);
        this.sender = sender;
    }

    @Override
    public void passwordChange(Account account) {
        addressAccount.get(account.getSocket().getAddress()).setPassword(null);
        sender.send("Enter password:", null, account.getSocket());
    }

    @Override
    public Account authenticate(SocketWrapper socket) {
        SocketAddress address = socket.getAddress();
        Account account = addressAccount.get(address);
        socket.close();
        for (AuthenticateStep step : chain) {
            if (step.handle(account, socket))
                return null;
        }
        return account;
    }
}
