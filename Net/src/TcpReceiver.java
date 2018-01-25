import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpReceiver implements Receiver {
    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;

    public TcpReceiver(int port) throws IOException {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public String accept() {
        try {
            if (socket == null || inputStream == null) {
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
            }
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            return dataInputStream.readUTF();
        } catch (IOException e) {
            socket = null;
            inputStream = null;
            System.out.println("Connection reset");
            return null;
        }

    }
}
