using FluentValidation;
using Lab5.Publisher.DTO.RequestDTO;

namespace Lab5.Publisher.Infrastructure.Validators;

public class NoteRequestDtoValidator : AbstractValidator<NoteRequestDto>
{
    public NoteRequestDtoValidator()
    {
        RuleFor(dto => dto.Content).Length(2, 2048);
    }
}