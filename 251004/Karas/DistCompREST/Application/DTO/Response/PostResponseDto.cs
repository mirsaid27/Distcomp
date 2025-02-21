using Domain.Entities;

namespace Application.Dto.Response;

public class PostResponseDto
{
    public long Id { get; set; }
    
    public long ArticleId { get; set; }
    public Article Article { get; set; }
    
    public string Content { get; set; }
}