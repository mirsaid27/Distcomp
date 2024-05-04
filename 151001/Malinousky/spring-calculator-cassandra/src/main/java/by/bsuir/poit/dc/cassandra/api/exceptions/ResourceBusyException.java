package by.bsuir.poit.dc.cassandra.api.exceptions;

/**
 * @author Name Surname
 * 
 */
public class ResourceBusyException extends ResourceException {
    public ResourceBusyException(String message, int code) {
	super(message, code);
    }
}
