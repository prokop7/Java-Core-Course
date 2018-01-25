package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class SendTask implements Runnable {
    private DataOutputStream dataOutputStream;

    SendTask(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String mes = scanner.nextLine();
        while (!Objects.equals(mes, "::exit")) {
            try {
                dataOutputStream.writeUTF(mes);
                dataOutputStream.flush();
            } catch (IOException e) {
                System.out.println("Connection aborted");
                return;
            }
            mes = scanner.nextLine();
        }
    }
}
