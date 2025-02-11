namespace Application.DTO;

public record UpdateUserRequestTo(long Id, string Login, string Password, string FirstName, string LastName);