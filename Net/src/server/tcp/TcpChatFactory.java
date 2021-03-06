package server.tcp;

import server.*;

import java.io.IOException;

public class TcpChatFactory implements ChatFactory {
    private int port;

    public TcpChatFactory(int port) {
        this.port = port;
    }

    @Override
    public Receiver newReceiver() throws IOException {
        return new TcpReceiver(port);
    }

    @Override
    public Sender newSender() {
        return new TcpSender();
    }

    @Override
    public Authenticator newAuthenticator(Sender sender) {
        return new TcpAuthenticator(sender);
    }
}
