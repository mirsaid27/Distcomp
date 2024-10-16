using FluentValidation;
using Task310.DTO.RequestDTO;

namespace Task310.Infrastructure.Validators
{
    public class NewsRequestDtoValidator : AbstractValidator<NewsRequestDto>
    {
        public NewsRequestDtoValidator()
        {
            RuleFor(dto => dto.Title).Length(2, 64);
            RuleFor(dto => dto.Content).Length(4, 2048);
        }
    }
}
