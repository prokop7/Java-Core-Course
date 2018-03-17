package services;

import dao.DatabaseProvider;
import dao.SqliteProvider;
import dao.exceptions.DaoException;
import dao.exceptions.DatabaseOpenException;
import dao.exceptions.InternalExecutionException;
import dao.exceptions.JdbcDriverNotFoundException;
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

    /**
     * Authenticate user
     * @param login user's login
     * @param password user's password
     * @return Generated token
     * @throws NullFieldException if login or password is null
     * @throws EmptyFieldException if login or password is empty
     */
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

    /**
     * @return new token as a new UUID
     */
    private String generateToken() {
        return String.valueOf(UUID.randomUUID());
    }

    /**
     * Register a new user into a system
     * @param login new user's login
     * @param password new user's password
     * @throws NullFieldException if login or password is null
     * @throws EmptyFieldException if login or password is empty
     * @throws InvalidFieldException if login or password contains invalid characters
     * @throws DuplicatedLoginException if login is already taken
     * @throws InternalDbException  if internal error occurs
     *                              doesn't depends on user's input data
     */
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

    /**
     * Authorize user by token
     * @param token user's token bearer
     * @return a login of the user
     */
    @Override
    public String authorize(String token) {
        try {
            return dao.getLoginByToken(token);
        } catch (InternalExecutionException e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * Eliminate a session token
     * @param token session's token
     */
    @Override
    public void logout(String token) throws InternalDbException {
        try {
            dao.removeToken(token);
        } catch (InternalExecutionException e) {
            logger.error(e);
            throw new InternalDbException();
        }
    }

    /**
     * Drops table and creates a new one.
     * @throws InternalDbException if database can not be initialized or dropped.
     */
    @Override
    public void reset() throws InternalDbException {
        try {
            this.dao.reset();
        } catch (InternalExecutionException |
                DatabaseOpenException |
                JdbcDriverNotFoundException e) {
            logger.error(e);
            throw new InternalDbException();
        }
    }
}
