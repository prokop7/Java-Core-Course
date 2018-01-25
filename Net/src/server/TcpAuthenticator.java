package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class TcpAuthenticator implements Authenticator {
    private ConcurrentHashMap<String, Account> loginPassword = new ConcurrentHashMap<>();

    @Override
    public Account authenticate(DataInputStream inputStream, DataOutputStream outputStream) {
        try {
            outputStream.writeUTF("Login:");
            String login = inputStream.readUTF();
            Account account = loginPassword.get(login);
            if (account == null)
                outputStream.writeUTF("Would you like to register? Enter your new password:");
            else
                outputStream.writeUTF("Password:");

            while (true) {
                String password = inputStream.readUTF();
                if (account != null) {
                    if (!account.getPassword().equals(password)) {
                        outputStream.writeUTF("Wrong password, repeat:");
                        continue;
                    }
                } else {
                    account = new Account(login, password);
                    loginPassword.put(login, account);
                }
                outputStream.writeUTF("Welcome " + account.getLogin());
                return account;
            }
        } catch (IOException e) {
            System.out.println("Connection reset");
        }
        return null;
    }
}
