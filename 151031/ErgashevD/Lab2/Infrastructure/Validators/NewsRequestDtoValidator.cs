using FluentValidation;
using Lab2.DTO.RequestDTO;

namespace Lab2.Infrastructure.Validators
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
