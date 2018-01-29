package server.udp;

import server.Authenticator;
import server.ChatFactory;
import server.Receiver;
import server.Sender;
import server.message_handlers.SocketHandler;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

public class UdpChatFactory implements ChatFactory {
    private int portIncoming;
    private int portOutgoing;

    public UdpChatFactory(int portIncoming, int portOutgoing) {
        this.portIncoming = portIncoming;
        this.portOutgoing = portOutgoing;
    }

    @Override
    public Receiver newReceiver() throws IOException {
        return new UdpReceiver(portIncoming);
    }

    @Override
    public Sender newSender() throws SocketException {
        return new UdpSender(portOutgoing);
    }

    @Override
    public Authenticator newAuthenticator(Sender sender) {
        return new UdpAuthenticator(sender);
    }
}
