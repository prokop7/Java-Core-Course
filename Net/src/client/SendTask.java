package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class SendTask implements Runnable {
    private DataOutputStream dataOutputStream;
    private boolean stopped = false;
    private Scanner scanner = new Scanner(System.in);

    SendTask(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    String readNext() {
        String mes = null;
        while (mes == null) {
            try {
                if (System.in.available() != 0) {
                    mes = scanner.nextLine();
                } else {
                    Thread.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return mes;
    }

    @Override
    public void run() {
        String mes = readNext();
        while (!Objects.equals(mes, "::exit")) {
            try {
                dataOutputStream.writeUTF(mes);
                dataOutputStream.flush();
            } catch (IOException e) {
                return;
            }
            mes = readNext();
        }
        stopped = true;
    }

    public boolean isStopped() {
        return stopped;
    }
}
