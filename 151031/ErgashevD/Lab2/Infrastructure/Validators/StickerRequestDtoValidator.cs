using FluentValidation;
using Lab2.DTO.RequestDTO;

namespace Lab2.Infrastructure.Validators
{
    public class StickerRequestDtoValidator : AbstractValidator<StickerRequestDto>
    {
        public StickerRequestDtoValidator()
        {
            RuleFor(dto => dto.Name).Length(2, 32);
        }
    }
}
