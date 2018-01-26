package server.udp;

import server.Account;
import server.tcp.CommonSender;
import server.SocketWrapper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpSender extends CommonSender {
    private static final int repeatNumber = 3;
    private DatagramSocket datagramSocket;

    UdpSender(int port) throws SocketException {
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
    public void send(String message, Account sender, SocketWrapper receiver) {
        try {
            String senderName = sender == null ? "Server" : sender.getLogin();
            DatagramPacket packet = ((UdpSocket) receiver).getWritablePacket(String.format("%s: %s", senderName, message));
            datagramSocket.send(packet);
        } catch (IOException e) {
            System.out.printf("Outgoing connection aborted: %s\n", receiver.getAddress());
        }
    }
}
