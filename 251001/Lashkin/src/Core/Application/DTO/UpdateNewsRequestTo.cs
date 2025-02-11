namespace Application.DTO;

public record UpdateNewsRequestTo(long Id, long UserId, string Title, string Content, DateTime Created, DateTime Modified);