using Publisher.Dto.Request.CreateTo;
using Publisher.Dto.Request.UpdateTo;
using Publisher.Dto.Response;
namespace Publisher.Services.Interfaces;

public interface IUserService
{
    Task<UserResponseTo> GetUserById(long id);
    Task<IEnumerable<UserResponseTo>> GetUsers();
    Task<UserResponseTo> CreateUser(CreateUserRequestTo createUserRequestTo);
    Task DeleteUser(long id);
    Task<UserResponseTo> UpdateUser(UpdateUserRequestTo modifiedUser);
}