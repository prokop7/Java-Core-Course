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
            else {
                if (account.getSocket() == null)
                    outputStream.writeUTF("Password:");
                else {
                    outputStream.writeUTF("Account already in use");
                    outputStream.close();
                    return null;
                }
            }

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
                return account;
            }
        } catch (IOException e) {
            System.out.println("Connection reset");
        }
        return null;
    }

    @Override
    public void passwordChange(Account account, DataInputStream inputStream, DataOutputStream outputStream) {
        try {
            outputStream.writeUTF("Enter a new password");
            String password = inputStream.readUTF();
            account.setPassword(password);
            assert (loginPassword.get(account.getLogin()).getPassword().equals(password));
            outputStream.writeUTF("Password changed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
