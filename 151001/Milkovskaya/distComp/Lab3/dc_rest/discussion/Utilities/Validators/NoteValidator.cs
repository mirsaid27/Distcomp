using discussion.Models;
using FluentValidation;

namespace discussion.Utilities.Validators;

public class NoteValidator : AbstractValidator<NoteRequestDto>
{
    public NoteValidator()
    {
        RuleFor(note => note.Content).NotEmpty().MinimumLength(2).MaximumLength(2048);
    }
}