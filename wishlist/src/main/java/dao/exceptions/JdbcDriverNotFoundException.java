package dao.exceptions;

import java.sql.SQLException;

public class JdbcDriverNotFoundException extends DaoException {
    private SQLException exception;

    public JdbcDriverNotFoundException(SQLException e) {
        super("Jdbc driver not found");
        exception = e;
    }

    public SQLException getException() {
        return exception;
    }
}
