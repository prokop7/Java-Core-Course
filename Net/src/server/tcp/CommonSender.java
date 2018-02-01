package server.tcp;

import server.Account;
import server.Sender;
import server.SocketWrapper;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class CommonSender implements Sender {
    private ConcurrentHashMap.KeySetView<Account, Boolean> accounts = ConcurrentHashMap.newKeySet();

    @Override
    protected void finalize() throws Throwable {
        accounts.clear();
        super.finalize();
    }

    @Override
    public void subscribe(Account socket) {
        accounts.add(socket);
    }

    @Override
    public void unsubscribe(Account socket) {
        accounts.remove(socket);
    }

    @Override
    public void send(String message, Account sender) {
        for (Account receiver : accounts) {
            if (sender == null || !receiver.getSocket().equals(sender.getSocket()))
                send(message, sender, receiver.getSocket());
        }
    }

    @Override
    public void send(String message, Account sender, SocketWrapper receiver) {
        try {
            String senderName = sender == null ? "Server" : sender.getLogin();
            receiver.write(String.format("%s: %s", senderName, message));
        } catch (IOException e) {
            System.out.printf("Outgoing connection aborted: %s\n", receiver.getAddress());
        }
    }

    @Override
    public void send(String message, Account sender, String login) {
        Account receiver = findByLogin(login);
        if (receiver == null) {
            System.out.println("No user with such port");
            return;
        }
        try {
            String senderName = sender == null ? "Server" : sender.getLogin();
            receiver.getSocket().write(String.format("%s: %s", senderName, message));
        } catch (IOException e) {
            System.out.printf("Outgoing connection aborted: %s\n", receiver.getSocket().getAddress());
        }
    }

    @Override
    public Account findByLogin(String login) {
        Account account = null;
        for (Account a : accounts) {
            if (a.getLogin().equals(login)) {
                account = a;
                break;
            }
        }
        return account;
    }
}
