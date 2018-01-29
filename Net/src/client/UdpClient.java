package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
import java.util.Scanner;

public class UdpClient {
    public static void main(String[] args) throws IOException {
        Random r = new Random();
        DatagramSocket socket = new DatagramSocket(r.nextInt(10000) + 8000);
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(packet);
                    System.out.println(new String(packet.getData()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        while (true) {
            String mes = scanner.nextLine();
            byte[] bytes = mes.getBytes();
            DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, InetAddress.getLocalHost(), 8080);
            socket.send(packet);
        }
    }
}
