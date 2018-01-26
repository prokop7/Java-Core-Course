package server;

import java.io.IOException;
import java.net.SocketException;

public interface ChatFactory {
    Receiver newReceiver() throws IOException;
    Sender newSender() throws SocketException;
    Authenticator newAuthenticator(Sender sender);
}
