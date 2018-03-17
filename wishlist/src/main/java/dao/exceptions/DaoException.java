package dao.exceptions;

public abstract class DaoException extends Exception {
    DaoException(String msg) {
        super(msg);
    }
}
