package by.bsuir.publisher.service.exceptions;

import by.bsuir.publisher.model.response.ErrorResponseTo;
import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class ResourceException extends RuntimeException {
    @Nullable
    private final ErrorResponseTo error;

    public ResourceException(String message, int code) {
        super(message);
        error = new ErrorResponseTo(code, message, null);
    }
}
