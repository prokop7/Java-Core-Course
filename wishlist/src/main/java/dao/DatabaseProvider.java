package dao;

import dao.exceptions.QueryExecutionException;
import dao.exceptions.StatementExecutionException;

public interface DatabaseProvider {

    boolean containsLogin(String login) throws QueryExecutionException;

    boolean checkPassword(String login, String password) throws QueryExecutionException;

    void assignToken(String login, String token) throws StatementExecutionException;

    void addRecord(String login, String password) throws StatementExecutionException;

    void removeToken(String token) throws StatementExecutionException;

    String getLoginByToken(String token) throws QueryExecutionException;

    void reset();
}
