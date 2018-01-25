package server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static Sender sender = new TcpSender();
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static Authenticator authenticator = new TcpAuthenticator();
    private static Receiver receiver;

    public static void main(String[] args) throws IOException {
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
                    socket.getLocalSocketAddress());
            Account account = authenticator.authenticate(receiver.getInputStream(socket), sender.getOutputStream(socket));
            if (account == null) {
//                sender.send("Try again later", null, socket);
//                socket.close();
                continue;
            }
            account.setSocket(socket);
            sender.subscribe(account.getSocket());

            executorService.submit(() -> {
                while (true) {
                    String mes = receiver.read(socket);
                    if (mes == null) {
                        sender.unsubscribe(socket);
                        return;
                    }
                    System.out.println(mes);
                    sender.send(mes, account);
                }
            });
        }
    }
}
