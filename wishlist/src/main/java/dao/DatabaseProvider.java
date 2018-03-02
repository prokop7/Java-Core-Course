package dao;

public interface DatabaseProvider {

    boolean containsLogin(String login);

    boolean checkPassword(String login, String password);

    void assignToken(String login, String token);

    void addRecord(String login, String password);

    void removeToken(String token);

    String getLoginByToken(String token);

    void reset();
}
