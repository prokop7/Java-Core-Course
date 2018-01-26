package server;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class TcpAuthenticator implements Authenticator {
    private ConcurrentHashMap<String, Account> loginPassword = new ConcurrentHashMap<>();

    @Override
    public Account authenticate(SocketWrapper socket) {
        try {
            socket.write("Login:");
            String login = socket.read();
            Account account = loginPassword.get(login);
            if (account == null)
                socket.write("Would you like to register? Enter your new password:");
            else {
                if (account.getSocket() == null)
                    socket.write("Password:");
                else {
                    socket.write("Account already in use");
                    socket.close();
                    return null;
                }
            }

            while (true) {
                String password = socket.read();
                if (account != null) {
                    if (!account.getPassword().equals(password)) {
                        socket.write("Wrong password, repeat:");
                        continue;
                    }
                } else {
                    account = new Account(login, password);
                    loginPassword.put(login, account);
                }
                account.setSocket(socket);
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
