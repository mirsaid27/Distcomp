using Application.Dto.Request;
using FluentValidation;

namespace Application.Validation;

public class PostRequestDtoValidator : AbstractValidator<PostRequestDto>
{
    public PostRequestDtoValidator()
    {
        RuleFor(Dto => Dto.Content).Length(2, 2048);
    }
}