import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int sendPort = Integer.parseInt(args[0]);
        int receivePort = Integer.parseInt(args[1]);
        System.out.printf("%s-%s", args[0], args[1]);
        Client client = new Client(sendPort, receivePort);
        client.start();
    }
}
