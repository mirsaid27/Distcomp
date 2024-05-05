using Publisher.Dto.Request.CreateTo;
using Publisher.Dto.Response;
using Publisher.Models;
using Riok.Mapperly.Abstractions;
namespace Publisher.Mappers;

[Mapper]
public static partial class UserMapper
{
    public static partial User ToEntity(this CreateUserRequestTo createUserRequestTo);
    public static partial UserResponseTo ToResponse(this User User);
    public static partial IEnumerable<UserResponseTo> ToResponse(this IEnumerable<User> Users);
}