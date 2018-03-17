package services.exceptions;

public abstract class ServiceException extends Exception{
    ServiceException(String s) {
        super(s);
    }
}
