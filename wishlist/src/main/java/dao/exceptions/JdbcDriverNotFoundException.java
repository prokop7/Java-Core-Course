package dao.exceptions;

public class JdbcDriverNotFoundException extends DaoException {
    public JdbcDriverNotFoundException() {
        super("Jdbc driver not found");
    }
}
