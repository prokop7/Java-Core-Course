package dao;

import dao.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PostrgeProvider implements DatabaseProvider {
    private static PostrgeProvider instance = null;
    private static final Logger logger = LogManager.getLogger(PostrgeProvider.class);
    private Connection connection;

    public static PostrgeProvider getProvider() throws DaoException {
        if (instance == null)
            instance = new PostrgeProvider("C:\\Users\\Monopolis\\Desktop\\local.db");
        return instance;
    }

    public static PostrgeProvider getProvider(String dbName) throws DaoException {
        if (instance == null)
            instance = new PostrgeProvider(dbName);
        return instance;
    }

    private PostrgeProvider(String dbName) throws DatabaseOpenException, JdbcDriverNotFoundException {
        Statement statement;
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
        } catch (SQLException e) {
            logger.error(e);
            throw new JdbcDriverNotFoundException(e);
        }
        try {
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", dbName));
            logger.debug(String.format("Database opened on \'%s\'", dbName));
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users\n" +
                    "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "login TEXT NOT NULL,\n" +
                    "password TEXT NOT NULL,\n" +
                    "token TEXT DEFAULT NULL\n" +
                    ");");
            statement.execute("CREATE UNIQUE INDEX IF NOT EXISTS users_id_uindex\n" +
                    "ON users (id);");
            statement.execute("CREATE UNIQUE INDEX IF NOT EXISTS users_login_uindex\n" +
                    "ON users (login);");
            statement.execute("CREATE UNIQUE INDEX IF NOT EXISTS users_token_uindex\n" +
                    "ON users (token);");
            statement.close();
        } catch (SQLException e) {
            logger.error(e);
            throw new DatabaseOpenException(e);
        }
    }

    @Override
    public boolean containsLogin(String login) throws SqlExecutionException {
        String res = getLoginBySql(String.format("SELECT login FROM users WHERE login='%s' LIMIT 1", login));
        return res != null;
    }

    @Override
    public boolean checkPassword(String login, String password) throws SqlExecutionException {
        String res = getLoginBySql(
                String.format("SELECT login FROM users WHERE login='%s' AND password='%s' LIMIT 1",
                        login,
                        password));
        return res != null;
    }

    private String getLoginBySql(String sql) throws SqlExecutionException {
        String resultLogin = null;
        try (ResultSet set = execute(sql, true)) {
            if (set != null) {
                try {
                    if (set.next())
                        resultLogin = set.getString("login");
                } catch (SQLException e) {
                    logger.error(e);
                }
                set.getStatement().close();
            }
            return resultLogin;
        } catch (SQLException e) {
            logger.error(e);
            return resultLogin;
        }
    }

    @Override
    public void assignToken(String login, String token) throws SqlExecutionException {
        execute(String.format("UPDATE users SET token='%s' WHERE login='%s'", token, login), false);
    }

    @Override
    public void addRecord(String login, String password) throws SqlExecutionException {
        execute(String.format("INSERT INTO users (login, password) VALUES (\"%s\", \"%s\")", login, password), false);
    }

    @Override
    public void removeToken(String token) throws SqlExecutionException {
        execute(String.format("UPDATE users SET token=NULL WHERE token=\"%s\"", token), false);
    }

    @Override
    public String getLoginByToken(String token) throws SqlExecutionException {
        return getLoginBySql(String.format("SELECT login FROM users WHERE token=\"%s\"", token));
    }

    @Override
    public void reset() {

    }

//    private ResultSet executeQuery(String sql) throws SqlExecutionException {
//        int repeatNumber = 3;
//        while (repeatNumber > 0)
//            try {
//                Statement statement = connection.createStatement();
//                return execute(statement, sql, true);
//            } catch (SQLException e) {
//                repeatNumber--;
//                logger.error(e);
//            }
//        throw new SqlExecutionException();
//    }

    private ResultSet execute(String sql, boolean isQuery) throws SqlExecutionException {
        int repeatNumber = 3;
        Statement statement = null;
        while (repeatNumber > 0)
            try {
                statement = connection.createStatement();
                break;
            } catch (SQLException e) {
                repeatNumber--;
                logger.error(e);
                if (repeatNumber == 0)
                    throw new SqlExecutionException();
            }
            
        try {
            return execute(statement, sql, isQuery);
        } catch (SQLException ignored) {
            throw new SqlExecutionException();
        }
    }

    private ResultSet execute(Statement statement, String sql, boolean isQuery) throws SQLException {
        int repeatNumber = 3;
        while (repeatNumber > 0)
            try {
                if (isQuery)
                    return statement.executeQuery(sql);
                else
                    statement.execute(sql);
                return null;
            } catch (SQLException e) {
                repeatNumber--;
                logger.error(e + ". SQL=" + sql);
                if (repeatNumber == 0)
                    throw e;
            }
        return null;
    }
}
