package services;

import dao.DatabaseProvider;
import dao.PostrgeProvider;
import dao.exceptions.DaoException;
import dao.exceptions.SqlExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.exceptions.*;

public class AuthorizationService {
    private DatabaseProvider dao;
    private static final Logger logger = LogManager.getLogger(AuthorizationService.class);

    public AuthorizationService() throws DbConnectionException {
        int repeatCount = 3;
        while (repeatCount-- > 0)
            try {
                this.dao = PostrgeProvider.getProvider();
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

    public String authenticate(String login, String password) {
        login = login.trim();
        password = password.trim();
        try {
            if (!dao.checkPassword(login, password)) {
                return null;
            } else {
                String token = generateToken(login, password);
                dao.assignToken(login, token);
                return token;
            }
        } catch (SqlExecutionException e) {
            logger.error("Can't connect to the database");
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
            logger.error("Can't connect to the database");
            throw new InternalDbException();
        }
    }

    public String authorize(String token) {
        try {
            return dao.getLoginByToken(token);
        } catch (SqlExecutionException e) {
            logger.error("Can't connect to the database");
            return null;
        }
    }

    public boolean logout(String token) {
        try {
            dao.removeToken(token);
            return true;
        } catch (SqlExecutionException e) {
            logger.error("Can't connect to the database");
            return false;
        }
    }

    public void reset() {
        this.dao.reset();
    }
}
