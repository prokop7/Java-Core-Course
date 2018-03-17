package dao.exceptions;

public class DatabaseOpenException extends DaoException {
    public DatabaseOpenException() {
        super("Can't open database");
    }
}
