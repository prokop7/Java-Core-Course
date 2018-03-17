package server.udp;

import server.SocketWrapper;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.Objects;

public class UdpSocket implements SocketWrapper {
    private DatagramPacket datagramPacket;
    private boolean closed = false;

    UdpSocket(DatagramPacket datagramPacket) {
        this.datagramPacket = datagramPacket;
    }

    DatagramPacket getPacket() {
        return datagramPacket;
    }

    @Override
    public void write(String s) {
        try {
            throw new NoSuchMethodException();
        } catch (NoSuchMethodException ignored) {
            System.out.println("Throw iff you tries to use Udp socket in Tcp server.");
        }
    }

    public DatagramPacket getWritablePacket(String s) {
        DatagramPacket packet = new DatagramPacket(s.getBytes(), s.length());
        packet.setAddress(datagramPacket.getAddress());
        packet.setPort(datagramPacket.getPort());
        return packet;
    }

    @Override
    public String read() {
        try {
            return new String(datagramPacket.getData(),0 ,datagramPacket.getLength(), "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
            return null;
        }
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public SocketAddress getAddress() {
        return datagramPacket.getSocketAddress();
    }

    @Override
    public int getPort() {
        return datagramPacket.getPort();
    }

    @Override
    public void close() {
        this.closed = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UdpSocket udpSocket = (UdpSocket) o;
        return Objects.equals(datagramPacket.getSocketAddress(), udpSocket.datagramPacket.getSocketAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(datagramPacket.getSocketAddress());
    }
}
