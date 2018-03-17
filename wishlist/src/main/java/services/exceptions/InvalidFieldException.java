package services.exceptions;

public class InvalidFieldException extends ServiceException {
    public InvalidFieldException() {
        super("Field contains invalid character");
    }
}
