package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public interface Authenticator {
    Account authenticate(DataInputStream inputStream, DataOutputStream outputStream);
}
