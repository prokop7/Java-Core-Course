package server;

import java.io.IOException;

public class TcpChatFactory implements ChatFactory {
    private int port;

    TcpChatFactory(int port) {
        this.port = port;
    }

    @Override
    public Receiver newReceiver() throws IOException {
        return new TcpReceiver(port);
    }

    @Override
    public Sender newSender() {
        return new CommonSender();
    }

    @Override
    public Authenticator newAuthenticator(Sender ignored) {
        return new TcpAuthenticator();
    }
}
