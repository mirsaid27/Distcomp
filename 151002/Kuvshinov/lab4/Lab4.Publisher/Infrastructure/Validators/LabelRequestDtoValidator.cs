using FluentValidation;
using Lab4.Publisher.DTO.RequestDTO;

namespace Lab4.Publisher.Infrastructure.Validators;

public class StickerRequestDtoValidator : AbstractValidator<StickerRequestDto>
{
    public StickerRequestDtoValidator()
    {
        RuleFor(dto => dto.Name).Length(2, 32);
    }
}