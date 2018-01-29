package server.message_handlers;

import server.Account;

public interface SocketHandler {
    boolean handle(String message, Account account);
}
