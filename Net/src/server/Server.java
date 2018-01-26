package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static Sender sender = new TcpSender();
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static Authenticator authenticator = new TcpAuthenticator();
    private static Receiver receiver;

    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0)
            port = Integer.parseInt(args[0]);

        try {
            receiver = new TcpReceiver(port);
        } catch (IOException e) {
            System.out.println("Server can't be open");
            return;
        }

        boolean isStopped = false;

        while (!isStopped) {
            Socket socket = receiver.acceptNew();
            System.out.printf("New user connected: %s:%s\n",
                    socket.getInetAddress(),
                    socket.getPort());
            Account account = authenticator.authenticate(receiver.getInputStream(socket), sender.getOutputStream(socket));
            if (account == null) {
                continue;
            }
            account.setSocket(socket);
            sender.subscribe(account.getSocket());

            sender.send(String.format("Welcome %s!", account.getLogin()), null);

            executorService.submit(() -> {
                while (true) {
                    String mes = receiver.read(socket);
                    if (mes == null) {
                        sender.send(String.format("%s has left chat", account.getLogin()), null);
                        sender.unsubscribe(socket);
                        account.setSocket(null);
                        return;
                    } else if (Objects.equals(mes, "!psw")) {
                        authenticator.passwordChange(account, receiver.getInputStream(socket), sender.getOutputStream(socket));
                        continue;
                    }
                    System.out.printf("%s: %s%n", account.getLogin(), mes);
                    sender.send(mes, account);
                }
            });
        }
    }
}
