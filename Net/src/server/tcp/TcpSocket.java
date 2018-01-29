package server.tcp;

import server.SocketWrapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Objects;

public class TcpSocket implements SocketWrapper {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    TcpSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    private InetAddress getInetAddress() {
        return socket.getInetAddress();
    }

    @Override
    public int getPort() {
        return socket.getPort();
    }

    @Override
    public void write(String s) throws IOException {
        dataOutputStream.writeUTF(s);
        dataOutputStream.flush();
    }

    @Override
    public String read() {
        try {
            return dataInputStream.readUTF();
        } catch (IOException e) {
            System.out.printf("Incoming connection aborted: %s:%s\n", socket.getInetAddress(), socket.getPort());
            return null;
        }
    }

    @Override
    public boolean isClosed() {
        return socket.isClosed();
    }

    @Override
    public SocketAddress getAddress() {
        return new InetSocketAddress(getInetAddress(), getPort());
    }

    @Override
    public void close() throws IOException {
        this.dataInputStream.close();
        this.dataOutputStream.close();
        this.socket.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcpSocket tcpSocket = (TcpSocket) o;
        return Objects.equals(socket, tcpSocket.socket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket);
    }
}
