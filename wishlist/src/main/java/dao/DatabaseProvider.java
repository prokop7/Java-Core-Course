package dao;

import dao.exceptions.DatabaseOpenException;
import dao.exceptions.InternalExecutionException;
import dao.exceptions.JdbcDriverNotFoundException;

public interface DatabaseProvider {

    boolean containsLogin(String login) throws InternalExecutionException;

    boolean checkPassword(String login, String password) throws InternalExecutionException;

    void assignToken(String login, String token) throws InternalExecutionException;

    void addRecord(String login, String password) throws InternalExecutionException;

    void removeToken(String token) throws InternalExecutionException;

    String getLoginByToken(String token) throws InternalExecutionException;

    void reset() throws InternalExecutionException, DatabaseOpenException, JdbcDriverNotFoundException;

    String getTokenByLogin(String login) throws InternalExecutionException;
}
