using System.Text.Json.Serialization;

namespace TaskREST.Dto.Response;

public record UserResponseTo(
    [property: JsonPropertyName("id")] long id,
    [property: JsonPropertyName("login")] string Login,
    [property: JsonPropertyName("password")]
    string Password,
    [property: JsonPropertyName("firstname")]
    string FirstName,
    [property: JsonPropertyName("lastname")]
    string LastName
);