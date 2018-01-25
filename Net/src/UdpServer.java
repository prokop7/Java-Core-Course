import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class UdpServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(8080);
        byte[] bytes = new byte[1024];
        DatagramPacket packet = new DatagramPacket(bytes, 1024);

        String mes;
        while (true) {
            ds.receive(packet);
            mes = new String(packet.getData());
            if ("q".equals(mes))
                return;
            System.out.println(mes);
        }
    }
}
