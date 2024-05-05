using Riok.Mapperly.Abstractions;
using TaskREST.Dto.Request.CreateTo;
using TaskREST.Dto.Request.UpdateTo;
using TaskREST.Dto.Response;
using TaskREST.Models;

namespace TaskREST.Mappers;

[Mapper]
public static partial class UserMapper
{
    public static partial User Map(UpdateUserRequestTo updateUserRequestTo);
    public static partial User Map(CreateUserRequestTo createUserRequestTo);
    public static partial UserResponseTo Map(User User);
    public static partial IEnumerable<UserResponseTo> Map(IEnumerable<User> Users);

    public static partial IEnumerable<User> Map(
        IEnumerable<UpdateUserRequestTo> UserRequestTos);
}