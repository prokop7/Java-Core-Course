package server;

public class Account {
    private String login;
    private String password;
    private SocketWrapper socket;

    Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Account() {
    }

    String getPassword() {
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
}
