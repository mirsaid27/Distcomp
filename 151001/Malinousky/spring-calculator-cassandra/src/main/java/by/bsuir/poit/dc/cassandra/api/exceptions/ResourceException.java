package by.bsuir.poit.dc.cassandra.api.exceptions;

import lombok.Getter;

/**
 * The wrapper around exception to make exception handler more lightweight.
 * This exception is used with violation of business logic rules.
 *
 * @author Name Surname
 * 
 */
@Getter
public class ResourceException extends RuntimeException {
    private final int code;

    public ResourceException(String message, int code) {
	super(message);
	this.code = code;
    }

    @Override

    public synchronized Throwable fillInStackTrace() {
	return this;
    }
}
