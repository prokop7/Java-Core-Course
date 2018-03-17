package client;

import java.io.DataInputStream;
import java.io.IOException;

public class ReceiveTask implements Runnable {
    private DataInputStream dataInputStream;

    ReceiveTask(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String mes = dataInputStream.readUTF();
                System.out.println(mes);
            } catch (IOException e) {
                System.out.println("Connection aborted, probably you need to enter any symbol");
                return;
            }
        }
    }

}
