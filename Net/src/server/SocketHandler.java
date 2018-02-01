package server;

public interface SocketHandler {
    boolean handle(String message, Account account);
}
