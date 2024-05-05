package by.bsuir.publisher.model.response;

public record ErrorResponseTo(
    int errorCode,
    String errorMessage,

    String[] errors
) {}