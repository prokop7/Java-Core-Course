package dao;

import dao.exceptions.DaoException;
import dao.exceptions.DatabaseOpenException;
import dao.exceptions.JdbcDriverNotFoundException;
import dao.exceptions.InternalExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class SqliteProvider implements DatabaseProvider {
    private static SqliteProvider instance = null;
    private static final Logger logger = LogManager.getLogger(SqliteProvider.class);
    private Connection connection;
    private final String containsLogin = "SELECT login FROM users WHERE login=? LIMIT 1";
    private final String checkLoginPassword = "SELECT login FROM users WHERE login=? AND password=? LIMIT 1";
    private final String assignToken = "UPDATE users SET token=? WHERE login=?";
    private final String insertRecord = "INSERT INTO users (login, password) VALUES (?, ?)";
    private final String removeToken = "UPDATE users SET token=NULL WHERE token=?";
    private final String loginByToken = "SELECT login FROM users WHERE token=?";
    private final String tokenByLogin = "SELECT token FROM users WHERE login=?";
    private final String dropTable = "DROP TABLE users";

    public static SqliteProvider getProvider() throws DaoException {
        if (instance == null)
            instance = new SqliteProvider("C:\\Users\\Monopolis\\Desktop\\local.db");
        return instance;
    }

    public static SqliteProvider getProvider(String dbName) throws DaoException {
        if (instance == null)
            instance = new SqliteProvider(dbName);
        return instance;
    }

    private SqliteProvider(String dbName) throws DatabaseOpenException, JdbcDriverNotFoundException {
        Statement statement;
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
        } catch (SQLException e) {
            logger.error(e);
            throw new JdbcDriverNotFoundException();
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
            throw new DatabaseOpenException();
        }
    }

    private static void setString(PreparedStatement stmt, int pos, String s) throws InternalExecutionException {
        try {
            stmt.setString(pos, s);
        } catch (SQLException e) {
            logger.error(e);
            throw new InternalExecutionException();
        }
    }

    private PreparedStatement createStatement(String sql) throws InternalExecutionException {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            logger.error(e);
            throw new InternalExecutionException();
        }
    }

    @Override
    public boolean containsLogin(String login) throws InternalExecutionException {
        PreparedStatement stmt = createStatement(containsLogin);
        setString(stmt, 1, login);
        String res = getFirstColumn(stmt);
        return res != null;
    }

    @Override
    public boolean checkPassword(String login, String password) throws InternalExecutionException {
        String res;
        PreparedStatement stmt = createStatement(checkLoginPassword);
        setString(stmt, 1, login);
        setString(stmt, 2, password);
        res = getFirstColumn(stmt);
        return res != null;
    }

    @Override
    public void assignToken(String login, String token) throws InternalExecutionException {
        PreparedStatement stmt = createStatement(assignToken);
        setString(stmt, 1, token);
        setString(stmt, 2, login);
        execute(stmt, false);
    }

    @Override
    public void addRecord(String login, String password) throws InternalExecutionException {
        PreparedStatement stmt = createStatement(insertRecord);
        setString(stmt, 1, login);
        setString(stmt, 2, password);
        execute(stmt, false);
    }

    @Override
    public void removeToken(String token) throws InternalExecutionException {
        PreparedStatement stmt = createStatement(removeToken);
        setString(stmt, 1, token);
        execute(stmt, false);
    }

    @Override
    public String getLoginByToken(String token) throws InternalExecutionException {
        String res;
        PreparedStatement stmt = createStatement(loginByToken);
        setString(stmt, 1, token);
        res = getFirstColumn(stmt);
        return res;
    }

    @Override
    public void reset() throws InternalExecutionException {
        PreparedStatement stmt = createStatement(dropTable);
        execute(stmt, false);
    }

    @Override
    public String getTokenByLogin(String login) throws InternalExecutionException {
        String res;
        PreparedStatement stmt = createStatement(tokenByLogin);
        setString(stmt, 1, login);
        res = getFirstColumn(stmt);
        return res;
    }

    private String getFirstColumn(PreparedStatement preparedStatement) throws InternalExecutionException {
        String resultLogin = null;
        try (ResultSet set = execute(preparedStatement, true)) {
            if (set != null) {
                try {
                    if (set.next())
                        resultLogin = set.getString(1);
                } catch (SQLException e) {
                    logger.error(e);
                }
                set.getStatement().close();
            }
            return resultLogin;
        } catch (SQLException e) {
            logger.error(e);
            return resultLogin;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }

    private ResultSet execute(PreparedStatement statement, boolean isQuery) throws InternalExecutionException {
        try {
            if (isQuery)
                return statement.executeQuery();
            statement.execute();
            statement.close();
            return null;
        } catch (SQLException e) {
            logger.error(e);
            throw new InternalExecutionException();
        }
    }
}
