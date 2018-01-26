package server;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;

public interface SocketWrapper extends Closeable {
    InetAddress getInetAddress();

    int getPort();

    void write(String s) throws IOException;

    String read();

    boolean isClosed();
}
