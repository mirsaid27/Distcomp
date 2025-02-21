using Application.Dto.Request;
using FluentValidation;

namespace Application.Validation;

public class MarkRequestDtoValidator : AbstractValidator<MarkRequestDto>
{
    public MarkRequestDtoValidator()
    {
        RuleFor(dto => dto.Name).Length(2, 32);
    }
}