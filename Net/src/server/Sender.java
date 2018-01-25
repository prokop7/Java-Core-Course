package server;

import java.io.DataOutputStream;
import java.net.Socket;

public interface Sender {
    void subscribe(Socket socket);
    void unsubscribe(Socket socket);
    void send(String message, Account sender);
    void send(String message, Account sender, Socket receiver);
    DataOutputStream getOutputStream(Socket socket);
}
