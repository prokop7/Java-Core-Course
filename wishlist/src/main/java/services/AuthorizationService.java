package services;

import dao.DatabaseProvider;
import dao.SqliteProvider;
import dao.exceptions.DaoException;
import dao.exceptions.SqlExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.exceptions.*;

public class AuthorizationService {
    private DatabaseProvider dao;
    private static final Logger logger = LogManager.getLogger(AuthorizationService.class);

    public AuthorizationService() throws DbConnectionException {
        this(null);
    }

    public AuthorizationService(String dbName) throws DbConnectionException  {
        int repeatCount = 3;
        while (repeatCount-- > 0)
            try {
            if (dbName == null)
                this.dao = SqliteProvider.getProvider();
            else
                this.dao = SqliteProvider.getProvider(dbName);
                break;
            } catch (DaoException e) {
                if (repeatCount == 0) {
                    logger.fatal(e);
                    throw new DbConnectionException("Can't establish connection");
                } else {
                    logger.error(e);
                }
            }
    }

    public String authenticate(String login, String password) throws NullFieldException, EmptyFieldException {
        if (login == null || password == null)
            throw new NullFieldException();
        login = login.trim();
        password = password.trim();
        if ("".equals(login) || "".equals(password))
            throw new EmptyFieldException();

        try {
            if (!dao.checkPassword(login, password)) {
                return null;
            } else {
                String token = generateToken(login, password);
                dao.assignToken(login, token);
                return token;
            }
        } catch (SqlExecutionException e) {
            logger.error(e);
            return null;
        }
    }

    private String generateToken(String login, String password) {
        return login + password;
    }

    public void register(String login, String password) throws
            NullFieldException,
            EmptyFieldException,
            DuplicatedLoginException,
            InternalDbException {
        if (login == null || password == null)
            throw new NullFieldException();
        login = login.trim();
        password = password.trim();
        if (login.isEmpty() || password.isEmpty())
            throw new EmptyFieldException();
        try {
            if (dao.containsLogin(login))
                throw new DuplicatedLoginException();
            dao.addRecord(login, password);
        } catch (SqlExecutionException e) {
            logger.error(e);
            throw new InternalDbException();
        }
    }

    public String authorize(String token) {
        try {
            return dao.getLoginByToken(token);
        } catch (SqlExecutionException e) {
            logger.error(e);
            return null;
        }
    }

    public boolean logout(String token) {
        try {
            dao.removeToken(token);
            return true;
        } catch (SqlExecutionException e) {
            logger.error(e);
            return false;
        }
    }

    public void reset() throws InternalDbException {
        try {
            this.dao.reset();
        } catch (SqlExecutionException e) {
            logger.error(e);
            throw new InternalDbException();
        }
    }
}
