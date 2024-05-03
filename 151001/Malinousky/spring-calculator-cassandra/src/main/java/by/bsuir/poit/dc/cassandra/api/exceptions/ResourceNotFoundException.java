package by.bsuir.poit.dc.cassandra.api.exceptions;

/**
 * @author Name Surname
 * 
 */

public class ResourceNotFoundException extends ResourceException {
    public ResourceNotFoundException(String message, int code) {
	super(message, code);
    }

}
