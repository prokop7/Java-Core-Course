package dao;

public interface DatabaseProvider {
    String authenticate(String login, String password);

    boolean authorize(String token);

    boolean register(String login, String password);

    void reset();
}
