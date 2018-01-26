package server.udp;

import server.Account;
import server.Authenticator;
import server.Sender;
import server.SocketWrapper;

import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class UdpAuthenticator implements Authenticator {
    private ConcurrentHashMap<SocketAddress, Account> addressAccount = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Account> loginPassword = new ConcurrentHashMap<>();
    private Sender sender;
    private long timeout = 60_000;

    UdpAuthenticator(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void passwordChange(Account account) {
        //TODO change password for user
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
                        return null;
                    }
                    sender.send("You have been disconnected due timeout", null, cachedAccount.getSocket());
                    sender.unsubscribe(cachedAccount.getSocket());
                    cachedAccount.setSocket(null);
                }
                account.setLogin(login);
                sender.send("Password:", null, socket);
                return null;
            }
            account.setLogin(login);
            loginPassword.put(login, account);
            sender.send("Would you like to register? Enter your new password:", null, socket);
            return null;
        } else if (account.getPassword() == null) {
            String psw = socket.read();
            Account cachedAccount = loginPassword.get(account.getLogin());
            if (cachedAccount.getPassword() != null) {
                if (!cachedAccount.getPassword().equals(psw)) {
                    sender.send("Wrong password, try again:", null, socket);
                    return null;
                }
            }
            account.setPassword(psw);
            account.setSocket(socket);
            account.updateActive();
            loginPassword.put(account.getLogin(), account);
            sender.subscribe(socket);
            sender.send("Welcome " + account.getLogin(), null);
            return null;
        } else if (account.getSocket() == null) {
            addressAccount.put(address, new Account());
            sender.send("Login:", null, socket);
            return null;
        }
        Account cachedAccount = loginPassword.get(account.getLogin());
        cachedAccount.updateActive();
        account.updateActive();
        cachedAccount.setSocket(socket);
        account.setSocket(socket);
        return account;
    }
}
