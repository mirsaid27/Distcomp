using Publisher.Models.DTO.DTOs;
using Publisher.Models.DTO.ResponseTo;

namespace Publisher.Services.Interfaces
{
    public interface IUserService
    {
        UserResponceTo CreateUser(UserRequestTo item);
        List<UserResponceTo> GetUsers();
        UserResponceTo GetUser(int id);
        UserResponceTo UpdateUser(UserRequestTo item);
        int DeleteUser(int id);
    }
}
