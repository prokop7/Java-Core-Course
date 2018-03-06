package services.exceptions;

public class InternalDbException extends ServiceException {
    public InternalDbException() {
        super("Internal Database exception occurs");
    }
}
