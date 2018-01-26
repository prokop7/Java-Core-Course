package server;

import server.udp.UdpChatFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final String pswChangeWord = "::psw";
    private static final String exitWord = "::exit";

    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0)
            port = Integer.parseInt(args[0]);
//        ChatFactory tcpFactory = new TcpChatFactory(port);
//        run(tcpFactory);
        ChatFactory udpFactory = new UdpChatFactory(port, port + 1);
        run(udpFactory);
    }

    private static void run(ChatFactory factory) {
        Sender sender;
        Receiver receiver;
        Authenticator authenticator;

        //TODO eliminate exception
        try {
            receiver = factory.newReceiver();
            sender = factory.newSender();
            authenticator = factory.newAuthenticator(sender);
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
                    if (mes == null || Objects.equals(mes, exitWord)) {
                        sender.send(String.format("%s has left chat", account.getLogin()), null);
                        sender.unsubscribe(socket);
                        account.setSocket(null);
                        return;
                    } else if (Objects.equals(mes, pswChangeWord)) {
                        authenticator.passwordChange(account);
                    } else {
                        System.out.printf("%s: %s%n", account.getLogin(), mes);
                        sender.send(mes, account);
                    }
                    if (socket.isClosed())
                        return;
                }
            });
        }
    }
}
