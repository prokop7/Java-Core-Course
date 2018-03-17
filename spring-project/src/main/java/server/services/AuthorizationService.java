package server.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.model.Account;
import server.persistence.AccountRepository;

@Service
public class AuthorizationService implements AuthService {
    private final AccountRepository accountRepository;
    private static final Logger logger = LogManager.getLogger(AuthorizationService.class);

    @Autowired
    public AuthorizationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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

        Account account = accountRepository.getAccountByLoginAndPassword(login, password);
        if (account == null) {
            return null;
        } else {
            String token = account.getToken();
            if (token == null) {
                token = generateToken();
                account.setToken(token);
            }
            accountRepository.save(account);
            return token;
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
            if (accountRepository.containsLogin(login))
                throw new DuplicatedLoginException();
            accountRepository.addRecord(login, password);
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
            return accountRepository.getLoginByToken(token);
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
            accountRepository.removeToken(token);
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
            this.accountRepository.reset();
        } catch (InternalExecutionException |
                DatabaseOpenException |
                JdbcDriverNotFoundException e) {
            logger.error(e);
            throw new InternalDbException();
        }
    }
}
