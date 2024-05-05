using FluentValidation;
using Lab4.Publisher.DTO.RequestDTO;

namespace Lab4.Publisher.Infrastructure.Validators;

public class NoteRequestDtoValidator : AbstractValidator<NoteRequestDto>
{
    public NoteRequestDtoValidator()
    {
        RuleFor(dto => dto.Content).Length(2, 2048);
    }
}