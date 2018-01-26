package server;

public interface Sender {
    void subscribe(SocketWrapper socket);
    void unsubscribe(SocketWrapper socket);
    void send(String message, Account sender);
    void send(String message, Account sender, SocketWrapper receiver);
}
