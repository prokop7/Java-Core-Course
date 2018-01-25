package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8080);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String mes;
        while (true) {
            mes = dataInputStream.readUTF();
            if ("!q".equals(mes))
                break;
            System.out.println(mes);

            Scanner scanner = new Scanner(System.in);
            mes = scanner.nextLine();
            dataOutputStream.writeUTF(mes);
            dataOutputStream.flush();
        }
        dataOutputStream.close();
    }

}