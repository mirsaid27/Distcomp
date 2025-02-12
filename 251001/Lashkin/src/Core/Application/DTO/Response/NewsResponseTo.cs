namespace Application.DTO.Response;

public record NewsResponseTo(long UserId, string Title, string Content, DateTime Created, DateTime Modified);