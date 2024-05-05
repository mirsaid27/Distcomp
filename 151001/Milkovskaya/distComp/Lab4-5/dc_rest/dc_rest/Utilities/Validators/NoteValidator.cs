using dc_rest.DTOs.RequestDTO;
using FluentValidation;

namespace dc_rest.Utilities.Validators;

public class NoteValidator : AbstractValidator<NoteRequestDto>
{
    public NoteValidator()
    {
        RuleFor(note => note.Content).NotEmpty().MinimumLength(2).MaximumLength(2048);
    }
}