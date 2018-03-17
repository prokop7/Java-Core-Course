package server;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketAddress;

/***
 * Wrapper for TCP and UDP socket.
 */
public interface SocketWrapper extends Closeable {
    /***
     * Write response for the socket.
     * @param s message to response
     * @throws IOException if connection is closed/interrupted/aborted
     */
    void write(String s) throws IOException;

    /***
     * Read string from the incoming connection through
     *      DataInputStream() for TCP socket
     *      Buffer for UDP packet.
     *          UDP packet should be read only once
     * @return incoming message
     */
    String read();

    /***
     * @return {@link SocketAddress} for the connection
     */
    SocketAddress getAddress();

    int getPort();
    boolean isClosed();
    void close();
}
