import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Client {
    private Sender sender;
    private Receiver receiver;

    Client(int sendPort, int receivePort) throws IOException {
        this.receiver = new TcpReceiver(receivePort);
        this.sender = new TcpSender(sendPort, 1_000, 100);
    }

    void start() {
        Thread clientThread = new Thread(this::sendingTask);
        Thread serverThread = new Thread(this::receivingTask);

        serverThread.start();
        clientThread.start();

        try {
            clientThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendingTask() {
        String mes = null;
        while (!"q".equals(mes)) {
            Scanner scanner = new Scanner(System.in);
            mes = scanner.nextLine();
            sender.send(mes);
        }

    }

    private void receivingTask() {
        while(true) {
            String message = this.receiver.accept();
            if (message != null)
                System.out.println(message);
        }
    }
}
