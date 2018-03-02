package services;

import dao.DatabaseProvider;
import dao.PostrgeProvider;
import dao.exceptions.DaoException;
import dao.exceptions.QueryExecutionException;
import dao.exceptions.StatementExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthorizationService {
    private DatabaseProvider dao;
    private static final Logger logger = LogManager.getLogger(AuthorizationService.class);

    public AuthorizationService() {
        int repeatCount = 3;
        while (repeatCount-- > 0)
            try {
                this.dao = PostrgeProvider.getProvider();
                break;
            } catch (DaoException e) {
                if (repeatCount == 0)
                    logger.fatal(e);
                else
                    logger.error(e);
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
        } catch (QueryExecutionException | StatementExecutionException e) {
            logger.error("Can't connect to the database");
            return null;
        }
    }

    private String generateToken(String login, String password) {
        return login + password;
    }

    public boolean register(String login, String password) {
        if (login == null || password == null)
            return false;
        login = login.trim();
        password = password.trim();
        if (login.isEmpty() || password.isEmpty())
            return false;
        try {
            if (dao.containsLogin(login))
                return false;
            dao.addRecord(login, password);
            return true;
        } catch (QueryExecutionException | StatementExecutionException e) {
            logger.error("Can't connect to the database");
            return false;
        }
    }

    public String authorize(String token) {
        try {
            return dao.getLoginByToken(token);
        } catch (QueryExecutionException e) {
            logger.error("Can't connect to the database");
            return null;
        }
    }

    public boolean logout(String token) {
        try {
            dao.removeToken(token);
            return true;
        } catch (StatementExecutionException e) {
            logger.error("Can't connect to the database");
            return false;
        }
    }

    public void reset() {
        this.dao.reset();
    }
}
