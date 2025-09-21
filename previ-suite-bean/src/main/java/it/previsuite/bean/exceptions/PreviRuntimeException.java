package it.previsuite.bean.exceptions;

public class PreviRuntimeException extends RuntimeException {

    public PreviRuntimeException() {
        super();
    }
    public PreviRuntimeException(String message) {
        super(message);
    }
    public PreviRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
    public PreviRuntimeException(Throwable cause) {
        super(cause);
    }
}