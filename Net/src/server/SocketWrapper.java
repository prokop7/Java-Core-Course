package server;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketAddress;

public interface SocketWrapper extends Closeable {

    void write(String s) throws IOException;

    String read();

    boolean isClosed();

    SocketAddress getAddress();
}
