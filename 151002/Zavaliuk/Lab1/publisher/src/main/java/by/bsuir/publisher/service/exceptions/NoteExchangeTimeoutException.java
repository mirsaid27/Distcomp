package by.bsuir.publisher.service.exceptions;

public class NoteExchangeTimeoutException extends RuntimeException {
    public NoteExchangeTimeoutException() {
        super();
    }

    public NoteExchangeTimeoutException(String message) {
        super(message);
    }

    public NoteExchangeTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteExchangeTimeoutException(Throwable cause) {
        super(cause);
    }

    protected NoteExchangeTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
