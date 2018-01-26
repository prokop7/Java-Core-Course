package server;

import server.udp.UdpChatFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

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
            //TODO move into while block
//            System.out.printf("New user connected: %s:%s\n",
//                    socket.getInetAddress(),
//                    socket.getPort());
            Account account = authenticator.authenticate(socket);
            if (account == null) {
                continue;
            }
//            sender.subscribe(account.getSocket());

//            sender.send(String.format("Welcome %s!", account.getLogin()), null);

            executorService.submit(() -> {
                while (true) {
                    String mes = socket.read();
                    if (mes == null || Objects.equals(mes, "::exit")) {
                        sender.send(String.format("%s has left chat", account.getLogin()), null);
                        sender.unsubscribe(socket);
                        account.setSocket(null);
                        return;
                    } else if (Objects.equals(mes, "!psw")) {
                        authenticator.passwordChange(account);
                        continue;
                    }
                    System.out.printf("%s: %s%n", account.getLogin(), mes);
                    sender.send(mes, account);
                    if (socket.isClosed())
                        return;
                }
            });
        }
    }
}
