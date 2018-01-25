package server;

import java.io.DataInputStream;
import java.net.Socket;

public interface Receiver {
    Socket acceptNew();
    String read(Socket socket);
    DataInputStream getInputStream(Socket socket);
}
