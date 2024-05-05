using Microsoft.EntityFrameworkCore;
using Publisher.Dto.Request.CreateTo;
using Publisher.Dto.Request.UpdateTo;
using Publisher.Dto.Response;
using Publisher.Exceptions;
using Publisher.Mappers;
using Publisher.Models;
using Publisher.Services.Interfaces;
using Publisher.Storage;
namespace Publisher.Services.Implementations;

public sealed class UserService(AppDbContext context) : IUserService
{
    public async Task<UserResponseTo> GetUserById(long id)
    {
        return (await context.Users.FindAsync(id))?.ToResponse()
            ?? throw new EntityNotFoundException($"User with id = {id} doesn't exist.");
    }

    public async Task<IEnumerable<UserResponseTo>> GetUsers()
    {
        return (await context.Users.ToListAsync()).ToResponse();
    }

    public async Task<UserResponseTo> CreateUser(CreateUserRequestTo createUserRequestTo)
    {
        User User = createUserRequestTo.ToEntity();
        await context.Users.AddAsync(User);
        await context.SaveChangesAsync();
        return User.ToResponse();
    }

    public async Task DeleteUser(long id)
    {
        var User = await context.Users.FindAsync(id);
        if (User == null) throw new EntityNotFoundException($"User with id = {id} doesn't exist.");

        context.Users.Remove(User);
        await context.SaveChangesAsync();
    }

    public async Task<UserResponseTo> UpdateUser(UpdateUserRequestTo modifiedUser)
    {
        User? User = await context.Users.FindAsync(modifiedUser.Id);
        if (User == null)
            throw new EntityNotFoundException($"User with id = {modifiedUser.Id} doesn't exist.");

        context.Entry(User).State = EntityState.Modified;

        User.Id = modifiedUser.Id;
        User.FirstName = modifiedUser.FirstName;
        User.LastName = modifiedUser.LastName;
        User.Login = modifiedUser.Login;
        User.Password = modifiedUser.Password;

        await context.SaveChangesAsync();
        return User.ToResponse();
    }
}
