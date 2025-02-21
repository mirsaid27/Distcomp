using Application.Dto.Request;
using FluentValidation;

namespace Application.Validation;

public class ArticleRequestDtoValidator : AbstractValidator<ArticleRequestDto>
{
    public ArticleRequestDtoValidator()
    {
        RuleFor(Dto => Dto.Title).Length(2, 64);
        RuleFor(Dto => Dto.Content).Length(4, 2048);
    }
}