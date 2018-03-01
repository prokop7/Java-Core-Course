package dao;

import java.util.HashMap;

public class PostgreProvider implements DatabaseProvider {
    private HashMap<String, String> loginPassword = new HashMap<>();

    public PostgreProvider() {
    }

    @Override
    public String authenticate(String login, String password) {
        login = login.trim();
        password = password.trim();
        if (!loginPassword.containsKey(login))
            return null;
        String expectedPsw = loginPassword.get(login);
        if (!expectedPsw.equals(password)) {
            return null;
        } else {
            return login + password;
        }
    }

    @Override
    public boolean authorize(String token) {
        return "123".equals(token);
    }

    @Override
    public boolean register(String login, String password) {
        if (login == null || password == null)
            return false;
        login = login.trim();
        password = password.trim();
        if (login.isEmpty() || password.isEmpty())
            return false;
        if (loginPassword.containsKey(login))
            return false;
        loginPassword.put(login, password);
        return true;
    }

    @Override
    public void reset() {
        loginPassword.clear();
    }
}
