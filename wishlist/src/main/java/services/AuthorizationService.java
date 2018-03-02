package services;

import dao.DatabaseProvider;
import dao.PostrgeProvider;
import dao.exceptions.DaoException;
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
        if (!dao.containsLogin(login) || !dao.checkPassword(login, password)) {
            return null;
        } else {
            String token = generateToken(login, password);
            dao.assignToken(login, token);
            return token;
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
        if (dao.containsLogin(login))
            return false;
        dao.addRecord(login, password);
        return true;
    }

    public String authorize(String token) {
        return dao.getLoginByToken(token);
    }

    public void logOut(String token) {
        dao.removeToken(token);
    }

    public void reset() {
        this.dao.reset();
    }
}
