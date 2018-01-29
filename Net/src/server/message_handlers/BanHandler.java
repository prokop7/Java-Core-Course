package server.message_handlers;

import server.Account;
import server.Sender;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BanHandler implements SocketHandler {
    private Sender sender;
    private static ConcurrentHashMap<Integer, Date> banList = new ConcurrentHashMap<>();

    public BanHandler(Sender sender) {
        this.sender = sender;
    }

    private boolean hasBan(Account account) {
        Date banDate = banList.get(account.getSocket().getPort());
        return banDate != null && banDate.getTime() - new Date().getTime() >= 0;
    }

    @Override
    public boolean handle(String message, Account account) {
        if (hasBan(account)) {
            sender.send("You have been banned", null, account.getSocket());
            return true;
        }
        Matcher m = Pattern.compile("::ban (\\d+) (\\d+)").matcher(message);
        if (!m.matches())
            return false;
        int port = Integer.parseInt(m.group(1));
        int time = Integer.parseInt(m.group(2));
        banList.put(port, new Date(new Date().getTime() + time));
        sender.send("You have been banned for " + time, null, port);
        return true;
    }
}
