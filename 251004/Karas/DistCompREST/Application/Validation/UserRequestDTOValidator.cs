using Application.Dto.Request;
using FluentValidation;

namespace Application.Validation;

public class EditorRequestDtoValidator : AbstractValidator<EditorRequestDto>
{
    public EditorRequestDtoValidator()
    {
        RuleFor(Dto => Dto.Login).Length(2, 64);
        RuleFor(Dto => Dto.Password).Length(8, 128);
        RuleFor(Dto => Dto.Firstname).Length(2, 64);
        RuleFor(Dto => Dto.Lastname).Length(2, 64);
    }
}