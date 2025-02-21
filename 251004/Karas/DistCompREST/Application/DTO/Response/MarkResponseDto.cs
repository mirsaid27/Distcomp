using Domain.Entities;

namespace Application.Dto.Response;

public class MarkResponseDto
{
    public long Id { get; set; }
    public string Name { get; set; }
    
    public List<Article> Articles { get; set; } = [];
}