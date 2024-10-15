using FluentValidation;
using Task310.DTO.RequestDTO;

namespace Task310.Infrastructure.Validators
{
    public class NoteRequestDtoValidator : AbstractValidator<NoteRequestDto>
    {
        public NoteRequestDtoValidator()
        {
            RuleFor(dto => dto.Content).Length(2, 2048);
        }
    }
}
