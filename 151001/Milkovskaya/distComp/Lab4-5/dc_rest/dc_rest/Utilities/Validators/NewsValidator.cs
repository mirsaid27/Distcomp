using dc_rest.DTOs.RequestDTO;
using FluentValidation;

namespace dc_rest.Utilities.Validators;

public class NewsValidator : AbstractValidator<NewsRequestDto>
{
    public NewsValidator()
    {
        RuleFor(news => news.Title).NotEmpty().MinimumLength(2).MaximumLength(64);
        RuleFor(news => news.Content).NotEmpty().MinimumLength(4).MaximumLength(2048);
    }
}