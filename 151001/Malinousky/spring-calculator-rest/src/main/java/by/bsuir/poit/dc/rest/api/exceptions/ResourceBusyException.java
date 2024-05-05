package by.bsuir.poit.dc.rest.api.exceptions;

/**
 * @author Name Surname
 * 
 */
public class ResourceBusyException extends ResourceException {
    public ResourceBusyException(String message, int code) {
	super(message, code);
    }
}
