namespace Application.DTO;

public record NewsResponseTo(long UserId, string Title, string Content, DateTime Created, DateTime Modified);