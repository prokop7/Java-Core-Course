package services.exceptions;

public class DuplicatedLoginException extends ServiceException {
    public DuplicatedLoginException() {
        super("Login already contains in database");
    }
}
