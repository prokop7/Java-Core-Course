package server;

/***
 * Sender responsible for
 *  1) subscribing/unsubscribing users for broadcasting
 *      {@link #subscribe(SocketWrapper)}
 *      {@link #unsubscribe(SocketWrapper)}
 *  2) sending message to certain user {@link #send(String, Account, SocketWrapper)}
 *  3) sending message to all users {@link #send(String, Account)}
 */
public interface Sender {
    void subscribe(SocketWrapper socket);
    void unsubscribe(SocketWrapper socket);
    void send(String message, Account sender);
    void send(String message, Account sender, SocketWrapper receiver);
    void send(String message, Account sender, int receiverPort);
}
