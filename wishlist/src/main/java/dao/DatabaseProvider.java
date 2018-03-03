package dao;

import dao.exceptions.SqlExecutionException;

public interface DatabaseProvider {

    boolean containsLogin(String login) throws SqlExecutionException;

    boolean checkPassword(String login, String password) throws SqlExecutionException;

    void assignToken(String login, String token) throws SqlExecutionException;

    void addRecord(String login, String password) throws SqlExecutionException;

    void removeToken(String token) throws SqlExecutionException;

    String getLoginByToken(String token) throws SqlExecutionException;

    void reset();
}
