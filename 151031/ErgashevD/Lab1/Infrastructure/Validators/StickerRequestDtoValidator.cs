using FluentValidation;
using Lab1.DTO.RequestDTO;

namespace Lab1.Infrastructure.Validators
{
    public class StickerRequestDtoValidator : AbstractValidator<StickerRequestDto>
    {
        public StickerRequestDtoValidator()
        {
            RuleFor(dto => dto.Name).Length(2, 32);
        }
    }
}
