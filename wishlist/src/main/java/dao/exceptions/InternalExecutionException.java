package dao.exceptions;

public class InternalExecutionException extends DaoException {
    public InternalExecutionException() {
        super("Internal exception occurs during execution");
    }
}
