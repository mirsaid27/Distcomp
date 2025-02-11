namespace Application.DTO;

public record UserResponseTo(long Id, string Login, string Password, string FirstName, string LastName);