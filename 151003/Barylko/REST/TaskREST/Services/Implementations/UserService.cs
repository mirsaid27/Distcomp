using Microsoft.EntityFrameworkCore;
using TaskREST.Dto.Request.CreateTo;
using TaskREST.Dto.Request.UpdateTo;
using TaskREST.Dto.Response;
using TaskREST.Exceptions;
using TaskREST.Services.Interfaces;
using TaskREST.Storage;
using UserMapper = TaskREST.Mappers.UserMapper;

namespace TaskREST.Services.Implementations;

public sealed class UserService(AppDbContext context) : IUserService
{
    public async Task<UserResponseTo> GetUserById(long id)
    {
        var User = await context.Users.FindAsync(id);
        if (User == null) throw new EntityNotFoundException($"User with id = {id} doesn't exist.");

        return UserMapper.Map(User);
    }

    public async Task<IEnumerable<UserResponseTo>> GetUsers()
    {
        return UserMapper.Map(await context.Users.ToListAsync());
    }

    public async Task<UserResponseTo> CreateUser(CreateUserRequestTo createUserRequestTo)
    {
        var User = UserMapper.Map(createUserRequestTo);
        await context.Users.AddAsync(User);
        await context.SaveChangesAsync();
        return UserMapper.Map(User);
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
        var User = await context.Users.FindAsync(modifiedUser.Id);
        if (User == null)
            throw new EntityNotFoundException($"User with id = {modifiedUser.Id} doesn't exist.");

        context.Entry(User).State = EntityState.Modified;

        User.Id = modifiedUser.Id;
        User.FirstName = modifiedUser.FirstName;
        User.LastName = modifiedUser.LastName;
        User.Login = modifiedUser.Login;
        User.Password = modifiedUser.Password;

        await context.SaveChangesAsync();
        return UserMapper.Map(User);
    }
}