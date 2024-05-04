using FluentValidation;
using Lab4.Discussion.DTO.RequestDTO;

namespace Lab4.Discussion.Infrastructure.Validators
{
    public class NoteRequestDtoValidator : AbstractValidator<NoteRequestDto>
    {
        public NoteRequestDtoValidator()
        {
            RuleFor(dto => dto.Content).Length(2, 2048);
        }
    }
}
