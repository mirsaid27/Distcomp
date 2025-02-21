namespace Application.Dto.Request;

public class PostRequestDto
{
    public long Id { get; set; }
    public long ArticleId { get; set; }
    public string Content { get; set; }
}