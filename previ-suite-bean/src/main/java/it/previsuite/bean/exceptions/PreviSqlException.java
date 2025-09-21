package it.previsuite.bean.exceptions;

public class PreviSqlException extends Exception {

    public PreviSqlException() {
        super();
    }
    public PreviSqlException(String message) {
        super(message);
    }
    public PreviSqlException(String message, Throwable cause) {
        super(message, cause);
    }
    public PreviSqlException(Throwable cause) {
        super(cause);
    }
}