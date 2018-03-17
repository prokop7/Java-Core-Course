package server.message_handlers;

import server.Account;
import server.Sender;
import server.SocketHandler;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BanHandler implements SocketHandler {
    private Sender sender;
    private static ConcurrentHashMap<Account, Date> banList = new ConcurrentHashMap<>();

    public BanHandler(Sender sender) {
        this.sender = sender;
    }

    private boolean hasBan(Account account) {
        Date banDate = banList.get(account);
        return banDate != null && banDate.getTime() - new Date().getTime() >= 0;
    }

    @Override
    public boolean handle(String message, Account account) {
        if (hasBan(account)) {
            sender.send("You have been banned", null, account.getSocket());
            return true;
        }
        Matcher m = Pattern.compile("::ban (\\S+) (\\d+)").matcher(message);
        if (!m.matches())
            return false;

        String login = m.group(1);
        int time = Integer.parseInt(m.group(2));
        Account banAccount = sender.findByLogin(login);
        if (banAccount == null) {
            sender.send("No user with such login", null, account.getSocket());
            return true;
        }
        banList.put(banAccount, new Date(new Date().getTime() + time));
        sender.send(String.format("You have been banned for %d sec", time / 1000), null, login);
        sender.send(
                String.format("%s has been banned by %s for %d sec",
                        banAccount.getLogin(),
                        account.getLogin(),
                        time / 1000),
                null);
        return true;
    }
}
