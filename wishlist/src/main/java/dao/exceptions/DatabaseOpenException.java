package dao.exceptions;

import java.sql.SQLException;

public class DatabaseOpenException extends DaoException {
    private SQLException exception;
    public DatabaseOpenException(SQLException e) {
        super("Can't open database");
        this.exception = e;
    }

    public SQLException getException() {
        return exception;
    }
}
