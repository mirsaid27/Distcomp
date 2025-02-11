namespace Domain.Entities;

public class News : BaseEntity
{
    public Guid UserId { get; set; }
    public string Title { get; set; } = null!;
    public string Content { get; set; } = null!;
    public DateTime Created { get; set; }
    public DateTime Modified { get; set; }
}