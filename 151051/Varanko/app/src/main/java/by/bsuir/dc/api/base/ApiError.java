package by.bsuir.dc.api.base;

public record ApiError(
        String errorCode,
        String errorMessage
) {}