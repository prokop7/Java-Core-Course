package server;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CommonSender implements Sender {
    private final ConcurrentHashMap.KeySetView<Account, Boolean> accounts = ConcurrentHashMap.newKeySet();

    @Override
    protected void finalize() throws Throwable {
        accounts.clear();
        super.finalize();
    }

    @Override
    public void subscribe(Account socket) {
        synchronized (accounts) {
            accounts.add(socket);
        }
    }

    @Override
    public void unsubscribe(Account socket) {
        synchronized (accounts) {
            accounts.remove(socket);
        }
    }

    @Override
    public void send(String message, Account sender) {
        synchronized (accounts) {
            for (Account receiver : accounts) {
                if (sender == null || !receiver.getSocket().equals(sender.getSocket()))
                    send(message, sender, receiver.getSocket());
            }
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
        Account account;
        synchronized (accounts) {
            //Here is stream API with lambda
            account = accounts.stream()
                    .filter(a -> a.getLogin().equals(login))
                    .findFirst()
                    .orElse(null);
        }
        return account;
    }
}
