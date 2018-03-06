package services.exceptions;

public class EmptyFieldException extends ServiceException {
    public EmptyFieldException() {
        super("Field is empty");
    }
}
