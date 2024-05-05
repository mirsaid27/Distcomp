using FluentValidation;
using Lab5.Publisher.DTO.RequestDTO;

namespace Lab5.Publisher.Infrastructure.Validators;

public class StickerRequestDtoValidator : AbstractValidator<StickerRequestDto>
{
    public StickerRequestDtoValidator()
    {
        RuleFor(dto => dto.Name).Length(2, 32);
    }
}