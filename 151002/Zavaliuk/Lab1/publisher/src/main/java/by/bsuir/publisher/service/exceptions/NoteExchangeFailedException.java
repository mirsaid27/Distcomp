package by.bsuir.publisher.service.exceptions;

public class NoteExchangeFailedException extends RuntimeException {
    public NoteExchangeFailedException() {
        super();
    }

    public NoteExchangeFailedException(String message) {
        super(message);
    }

    public NoteExchangeFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteExchangeFailedException(Throwable cause) {
        super(cause);
    }
}