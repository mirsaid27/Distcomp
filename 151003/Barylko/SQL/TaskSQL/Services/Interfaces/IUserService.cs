using TaskREST.Dto.Request.CreateTo;
using TaskREST.Dto.Request.UpdateTo;
using TaskREST.Dto.Response;

namespace TaskREST.Services.Interfaces;

public interface IUserService
{
    Task<UserResponseTo> GetUserById(long id);
    Task<IEnumerable<UserResponseTo>> GetUsers();
    Task<UserResponseTo> CreateUser(CreateUserRequestTo createUserRequestTo);
    Task DeleteUser(long id);
    Task<UserResponseTo> UpdateUser(UpdateUserRequestTo modifiedUser);
}