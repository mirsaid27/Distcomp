using System;
using Domain.Models;
using Domain.Repositories;
using Domain.Shared;
using Infrastructure.Repositories.Interfaces;

namespace Infrastructure.Repositories.Postgres;

public class UserRepositoryPg : IPgRepository, IUserRepository
{
    public Task<Result<UserModel>> CreateUser(UserModel user)
    {
        throw new NotImplementedException();
    }

    public Task<Result> DeleteUser(long id)
    {
        throw new NotImplementedException();
    }

    public Task<Result<UserModel>> GetUserById(long id)
    {
        throw new NotImplementedException();
    }

    public Task<Result<IEnumerable<UserModel>>> GetUsers()
    {
        throw new NotImplementedException();
    }

    public Task<Result<UserModel>> UpdateUser(UserModel user)
    {
        throw new NotImplementedException();
    }
}
