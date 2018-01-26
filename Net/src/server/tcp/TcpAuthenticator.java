package server.tcp;

import server.Account;
import server.Authenticator;
import server.Sender;
import server.SocketWrapper;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class TcpAuthenticator implements Authenticator {
    private final Sender sender;
    private ConcurrentHashMap<String, Account> loginPassword = new ConcurrentHashMap<>();

    public TcpAuthenticator(Sender sender) {
        this.sender = sender;
    }

    @Override
    public Account authenticate(SocketWrapper socket) {
        try {
            sender.send("Login:", null, socket);
            String login = socket.read();
            Account account = loginPassword.get(login);
            if (account == null)
                sender.send("Would you like to register? Enter your new password:", null, socket);
            else {
                if (account.getSocket() == null)
                    sender.send("Password:", null, socket);
                else {
                    sender.send("Account already in use", null, socket);
                    socket.close();
                    return null;
                }
            }

            while (true) {
                String password = socket.read();
                if (account != null) {
                    if (!account.getPassword().equals(password)) {
                        sender.send("Wrong password, repeat:", null, socket);
                        continue;
                    }
                } else {
                    account = new Account(login, password);
                    loginPassword.put(login, account);
                }
                account.setSocket(socket);
                sender.subscribe(socket);
                sender.send("Welcome " + account.getLogin(), null);
                return account;
            }
        } catch (IOException e) {
            System.out.println("Connection reset");
        }
        return null;
    }

    @Override
    public void passwordChange(Account account) {
        try {
            SocketWrapper socket = account.getSocket();
            socket.write("Enter a new password");
            String password = socket.read();
            account.setPassword(password);
            assert (loginPassword.get(account.getLogin()).getPassword().equals(password));
            socket.write("Password changed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
