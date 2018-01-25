package server;

import java.net.Socket;

public class Account {
    private String login;
    private String password;
    private Socket socket;

    Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
