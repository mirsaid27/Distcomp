using FluentValidation;
using Lab3.Publisher.DTO.RequestDTO;

namespace Lab3.Publisher.Infrastructure.Validators;

public class StickerRequestDtoValidator : AbstractValidator<StickerRequestDto>
{
    public StickerRequestDtoValidator()
    {
        RuleFor(dto => dto.Name).Length(2, 32);
    }
}