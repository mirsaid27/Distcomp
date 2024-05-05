package by.bsuir.discussion.model.response;

public record ErrorResponseTo(
    int errorCode,
    String errorMessage,

    String[] errors
) {}