package server;

import server.message_handlers.*;
import server.tcp.TcpChatFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0)
            port = Integer.parseInt(args[0]);
        ChatFactory tcpFactory = new TcpChatFactory(port);
        run(tcpFactory);
//        ChatFactory udpFactory = new UdpChatFactory(port, port + 1);
//        run(udpFactory);
    }

    private static void run(ChatFactory factory) {
        Sender sender;
        Receiver receiver;
        Authenticator authenticator;
        List<SocketHandler> handlerList;

        //TODO eliminate exception
        try {
            receiver = factory.newReceiver();
            sender = factory.newSender();
            authenticator = factory.newAuthenticator(sender);
            handlerList = getHandlerList(sender, authenticator);
        } catch (IOException e) {
            System.out.println("Server can't be open");
            return;
        }

        boolean isStopped = false;

        //TODO make stopping for server
        while (!isStopped) {
            SocketWrapper socket = receiver.acceptNew();
            executorService.submit(() -> {
                Account account = null;
                while (true) {
                    if (account == null)
                        account = authenticator.authenticate(socket);

                    if (account == null) {
                        if (socket.isClosed())
                            return;
                        continue;
                    }

                    String mes = socket.read();
                    for (SocketHandler handler : handlerList) {
                        if (handler.handle(mes, account))
                            if (socket.isClosed())
                                return;
                    }
                }
            });
        }
    }

    private static List<SocketHandler> getHandlerList(Sender sender, Authenticator authenticator) {
        List<SocketHandler> handlerList = new ArrayList<>();
        handlerList.add(new BanHandler(sender));
        handlerList.add(new ExitHandler(sender));
        handlerList.add(new PasswordChangingHandler(authenticator));
        handlerList.add(new BroadcastHandler(sender));
        return handlerList;
    }
}
