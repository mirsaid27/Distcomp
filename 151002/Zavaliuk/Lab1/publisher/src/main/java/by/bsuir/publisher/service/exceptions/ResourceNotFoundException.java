package by.bsuir.publisher.service.exceptions;

public class ResourceNotFoundException extends ResourceException {
    public ResourceNotFoundException(String message, int code) {
        super(message, code);
    }
}
