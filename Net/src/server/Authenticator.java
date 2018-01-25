package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public interface Authenticator {
    Account authenticate(DataInputStream inputStream, DataOutputStream outputStream);
    void passwordChange(Account account, DataInputStream inputStream, DataOutputStream outputStream);
}
