package services.exceptions;

public class NullFieldException extends ServiceException {
    public NullFieldException() {
        super("One of the field is empty");
    }
}
