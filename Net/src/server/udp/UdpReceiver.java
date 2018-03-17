package server.udp;

import server.Receiver;
import server.SocketWrapper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpReceiver implements Receiver {
    private static final int repeatNumber = 3;
    private DatagramSocket datagramSocket;
    private int packetLength = 1024;


    UdpReceiver(int port, int packetLength) throws SocketException {
        this(port);
        this.packetLength = packetLength;
    }

    UdpReceiver(int port) throws SocketException {
        int i = repeatNumber;
        while (i > 0) {
            i--;
            try {
                this.datagramSocket = new DatagramSocket(port);
                return;
            } catch (IOException e) {
                System.out.printf("Can't run server on port%d\n", port);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                if (i == 0)
                    throw e;
            }
        }
    }

    @Override
    public SocketWrapper acceptNew() {
        byte[] bytes = new byte[packetLength];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, packetLength);
        try {
            datagramSocket.receive(datagramPacket);
            return new UdpSocket(datagramPacket);
        } catch (IOException e) {
            System.out.println("Connection reset");
            return null;
        }
    }
}
