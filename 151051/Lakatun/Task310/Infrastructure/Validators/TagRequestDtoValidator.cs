using FluentValidation;
using Task310.DTO.RequestDTO;

namespace Task310.Infrastructure.Validators
{
    public class TagRequestDtoValidator : AbstractValidator<TagRequestDto>
    {
        public TagRequestDtoValidator()
        {
            RuleFor(dto => dto.Name).Length(2, 32);
        }
    }
}
