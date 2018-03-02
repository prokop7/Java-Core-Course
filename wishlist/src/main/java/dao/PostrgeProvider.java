package dao;

import dao.exceptions.DaoException;
import dao.exceptions.DatabaseOpenException;
import dao.exceptions.JdbcDriverNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostrgeProvider implements DatabaseProvider {
    private static PostrgeProvider instance = null;
    private static final Logger logger = LogManager.getLogger(PostrgeProvider.class);
    private Connection connection;

    public static PostrgeProvider getProvider() throws DaoException {
        if (instance == null)
            instance = new PostrgeProvider("local.db");
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
                    "id INTEGER NOT NULL,\n" +
                    "login TEXT NOT NULL,\n" +
                    "password TEXT NOT NULL,\n" +
                    "token TEXT DEFAULT NULL\n" +
                    ");\n" +
                    "CREATE UNIQUE INDEX users_id_uindex\n" +
                    "ON users (id);\n" +
                    "CREATE UNIQUE INDEX users_login_uindex\n" +
                    "ON users (login);\n" +
                    "CREATE UNIQUE INDEX users_token_uindex\n" +
                    "ON users (token);\n");
            statement.close();
        } catch (SQLException e) {
            logger.error(e);
            throw new DatabaseOpenException(e);
        }
    }

    @Override
    public boolean containsLogin(String login) {
        return false;
    }

    @Override
    public boolean checkPassword(String login, String password) {
        return false;
    }

    @Override
    public void assignToken(String login, String token) {

    }

    @Override
    public void addRecord(String login, String password) {
        try (Statement statement = connection.createStatement()) {
            try {
                statement.execute(String.format("INSERT INTO users (login, password) VALUES (\"%s\", \"%s\")", login, password));
            } catch (SQLException e) {
                //TODO exception handling
                logger.error(e);
            }
        } catch (SQLException e) {
            //TODO exception handling
            logger.error(e);
        }
    }

    @Override
    public void removeToken(String token) {

    }

    @Override
    public String getLoginByToken(String token) {
        return null;
    }

    @Override
    public void reset() {

    }
}
