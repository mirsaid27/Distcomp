using dc_rest.DTOs.ResponseDTO;
using dc_rest.Exceptions;
using Microsoft.AspNetCore.Diagnostics;

namespace dc_rest.Middleware;

public class GlobalExceptionHandler : IExceptionHandler
{
    public async ValueTask<bool> TryHandleAsync(HttpContext httpContext, Exception exception, CancellationToken cancellationToken)
    {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto()
        {
            errorMessage = exception.Message,
        };
        switch (exception)
        {
            case ValidatinonException validationException:
            {
                httpContext.Response.StatusCode = StatusCodes.Status400BadRequest;
                errorResponseDto.code = validationException.Code;
                break;
            }
            case NotFoundException notFoundException:
            {
                httpContext.Response.StatusCode = StatusCodes.Status404NotFound;
                errorResponseDto.code = notFoundException.Code;
                break;
            }
            case UniqueConstraintException uniqueConstraintException:
            {
                httpContext.Response.StatusCode = StatusCodes.Status403Forbidden;
                errorResponseDto.code = uniqueConstraintException.Code;
                break;
            }
            case ForeignKeyViolation foreignKeyViolation:
            {
                httpContext.Response.StatusCode = StatusCodes.Status403Forbidden;
                errorResponseDto.code = foreignKeyViolation.Code;
                break;
            }
        }
        await httpContext.Response.WriteAsJsonAsync(errorResponseDto, cancellationToken);
        return true;
    }
}