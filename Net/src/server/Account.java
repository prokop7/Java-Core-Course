package server;

import java.util.Date;

public class Account {
    private String login;
    private String password;
    private SocketWrapper socket;
    private long lastAction;

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
        this.lastAction = new Date().getTime();
    }

    public Account() {
        this.lastAction = new Date().getTime();
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setSocket(SocketWrapper socket) {
        this.socket = socket;
    }

    public SocketWrapper getSocket() {
        return socket;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void updateActive() {
        lastAction = new Date().getTime();
    }

    public long getLastAction() {
        return lastAction;
    }
}
