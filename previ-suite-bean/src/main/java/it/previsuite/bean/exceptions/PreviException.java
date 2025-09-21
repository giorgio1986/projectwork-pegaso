package it.previsuite.bean.exceptions;

public class PreviException extends Exception {

    public PreviException() {
        super();
    }
    public PreviException(String message) {
        super(message);
    }
    public PreviException(String message, Throwable cause) {
        super(message, cause);
    }
    public PreviException(Throwable cause) {
        super(cause);
    }
}