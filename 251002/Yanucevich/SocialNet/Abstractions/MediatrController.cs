using System;
using Domain.Shared;
using Domain.Shared.Validation;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace SocialNet.Abstractions;

[ApiController]
public class MediatrController : ControllerBase
{
    protected readonly IMediator _mediator;
    protected MediatrController(IMediator mediator) => _mediator = mediator;

    protected IActionResult HandleFailure(Result result) =>
        result switch{
            { IsSuccess:true } => throw new InvalidOperationException(),
            IValidationResult validationResult =>
                BadRequest(CreateProblemDetails(
                    "ValidationError",
                    StatusCodes.Status400BadRequest,
                    result.Error,
                    validationResult.Errors
                )),
            _ =>
                BadRequest(CreateProblemDetails(
                    "Bad request",
                    StatusCodes.Status400BadRequest,
                    result.Error
                ))
        };

    private static ProblemDetails CreateProblemDetails(
        string title, 
        int status,
        Error error,
        Error[]? errors = null
    ) => new ()
    {
        Title = title,
        Type = error.Code,
        Detail = error.Message,
        Status = status,
        Extensions = { {nameof(errors), errors} }
    };
}
