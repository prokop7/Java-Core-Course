package server.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {
    private Account() {
    }

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Id
    private String login;
    private String password;
    private String token;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
