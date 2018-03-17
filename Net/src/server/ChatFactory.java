package server;

import server.message_handlers.BanHandler;
import server.message_handlers.BroadcastHandler;
import server.message_handlers.ExitHandler;
import server.message_handlers.PasswordChangingHandler;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public interface ChatFactory {
    Receiver newReceiver() throws IOException;
    Sender newSender() throws SocketException;
    Authenticator newAuthenticator(Sender sender);

    default List<SocketHandler> getHandlerList(Sender sender, Authenticator authenticator) {
        List<SocketHandler> handlerList = new ArrayList<>();
        handlerList.add(new BanHandler(sender));
        handlerList.add(new ExitHandler(sender));
        handlerList.add(new PasswordChangingHandler(authenticator));
        handlerList.add(new BroadcastHandler(sender));
        return handlerList;
    }
}
