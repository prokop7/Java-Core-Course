package services.exceptions;

public class DbConnectionException extends ServiceException {
    public DbConnectionException() {
        super("Can't establish connection to the database");
    }
}
