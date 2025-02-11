namespace Domain.Entities;

public class Notice : BaseEntity
{
    public Guid NewsId { get; set; }
    public string Content { get; set; } = null!;
}