using Domain.Entities;

namespace Application.Dto.Response;

public class ArticleResponseDto
{
    public long Id { get; set; }
    public string Title { get; set; }
    
    public long EditorId { get; set; }
    public Editor Editor { get; set; }

    public List<Post> Posts { get; set; }
    
    public string Content { get; set; }
    public DateTime Created { get; set; }
    public DateTime Modified { get; set; }

    public List<Mark> Marks { get; set; } = [];

}