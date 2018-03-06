package services;

import dao.DatabaseProvider;
import dao.SqliteProvider;
import dao.exceptions.DaoException;
import dao.exceptions.InternalExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.exceptions.*;

import java.util.UUID;

public class AuthorizationService implements AuthService {
    private DatabaseProvider dao;
    private static final Logger logger = LogManager.getLogger(AuthorizationService.class);

    public AuthorizationService() throws DbConnectionException {
        this(null);
    }

    public AuthorizationService(String dbName) throws DbConnectionException {
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
                    throw new DbConnectionException();
                } else {
                    logger.error(e);
                }
            }
    }

    @Override
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
                String token = dao.getTokenByLogin(login);
                if (token == null) {
                    token = generateToken();
                    dao.assignToken(login, token);
                }
                return token;
            }
        } catch (InternalExecutionException e) {
            logger.error(e);
            return null;
        }
    }

    private String generateToken() {
        return String.valueOf(UUID.randomUUID());
    }

    @Override
    public void register(String login, String password) throws
            NullFieldException,
            EmptyFieldException,
            InvalidFieldException,
            DuplicatedLoginException,
            InternalDbException {
        if (login == null || password == null)
            throw new NullFieldException();
        login = login.trim();
        password = password.trim();
        if (login.isEmpty() || password.isEmpty())
            throw new EmptyFieldException();

        if (Validator.isInvalid(login) || Validator.isInvalid(password))
            throw new InvalidFieldException();

        try {
            if (dao.containsLogin(login))
                throw new DuplicatedLoginException();
            dao.addRecord(login, password);
        } catch (InternalExecutionException e) {
            logger.error(e);
            throw new InternalDbException();
        }
    }

    @Override
    public String authorize(String token) {
        try {
            return dao.getLoginByToken(token);
        } catch (InternalExecutionException e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public void logout(String token) {
        try {
            dao.removeToken(token);
        } catch (InternalExecutionException e) {
            logger.error(e);
        }
    }

    @Override
    public void reset() throws InternalDbException {
        try {
            this.dao.reset();
        } catch (InternalExecutionException e) {
            logger.error(e);
            throw new InternalDbException();
        }
    }
}
