package by.bsuir.poit.dc.cassandra.api.exceptions;

/**
 * @author Name Surname
 * 
 */
public class ContentNotValidException extends ResourceException{
    public ContentNotValidException(String message, int code) {
	super(message, code);
    }
}
