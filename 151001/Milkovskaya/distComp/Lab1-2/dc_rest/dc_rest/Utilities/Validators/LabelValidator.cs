using dc_rest.DTOs.RequestDTO;
using FluentValidation;

namespace dc_rest.Utilities.Validators;

public class LabelValidator : AbstractValidator<LabelRequestDto>
{
    public LabelValidator()
    {
        RuleFor(label => label.Name).NotEmpty().MinimumLength(2).MaximumLength(32);
    }
}