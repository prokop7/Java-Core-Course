import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TcpSender implements Sender {
    private DataOutputStream dataOutputStream;

    public TcpSender(int port, int connectionTimeout, int repeatNumber) throws IOException {
        for (int i = 0; i < repeatNumber; i++) {
            try {
                Socket socket = new Socket("127.0.0.1", port);
                this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
                break;
            } catch (IOException e) {
                try {
                    Thread.sleep(connectionTimeout);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                if (i == repeatNumber - 1)
                    throw e;
                System.out.println("RECONNECT");
            }
        }
    }

    @Override
    public void send(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
