import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();

        Scanner scanner = new Scanner(System.in);
        String mes = null;
        while (!"q".equals(mes)) {
            mes = scanner.nextLine();
            byte[] buffer = mes.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, mes.length());
            packet.setAddress(InetAddress.getLocalHost());
            packet.setPort(8080);
            socket.send(packet);
        }
        socket.close();

    }
}
