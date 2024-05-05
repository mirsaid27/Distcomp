using dc_rest.DTOs.RequestDTO;
using FluentValidation;

namespace dc_rest.Utilities.Validators;

public class CreatorValidator : AbstractValidator<CreatorRequestDto>
{
    public CreatorValidator()
    {
        RuleFor(creator => creator.Login).NotEmpty().MinimumLength(2).MaximumLength(64);
        RuleFor(creator => creator.Password).NotEmpty().MinimumLength(8).MaximumLength(128);
        RuleFor(creator => creator.Firstname).NotEmpty().MinimumLength(2).MaximumLength(64);
        RuleFor(creator => creator.Lastname).NotEmpty().MinimumLength(2).MaximumLength(64);
    }
}