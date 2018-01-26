package server;

import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class UdpAuthenticator implements Authenticator {
    private ConcurrentHashMap<SocketAddress, Account> addressAccount = new ConcurrentHashMap<>();
    private ConcurrentHashMap<SocketAddress, Account> loginPassword = new ConcurrentHashMap<>();
    private Sender sender;

    //    public static final String gratitudeMessage = "!!hello!!";
    UdpAuthenticator(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void passwordChange(Account account) {

    }

    @Override
    public Account authenticate(SocketWrapper socket) {
        DatagramPacket packet = ((UdpSocket) socket).getPacket();
        SocketAddress address = packet.getSocketAddress();
        Account account = addressAccount.get(address);
        if (account == null) {
            account = new Account();
            addressAccount.put(address, account);
            sender.send("Login:", null, socket);
            return null;
        } else if (account.getLogin() == null) {
            account.setLogin(socket.read());
            sender.send("Password:", null, socket);
            return null;
        } else if (account.getPassword() == null) {
            account.setPassword(socket.read());
            sender.send("Welcome " + account.getLogin(), null, socket);
            return null;
        }
        account.setSocket(socket);
        return account;
    }
}
