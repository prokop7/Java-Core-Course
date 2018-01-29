package server;

/***
 * Receiver is responsible for accepting new connections
 */
public interface Receiver {
    SocketWrapper acceptNew();
}
