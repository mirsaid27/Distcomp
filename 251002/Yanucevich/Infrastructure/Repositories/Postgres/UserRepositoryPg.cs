using System;
using Domain.Models;
using Domain.Repositories;
using Domain.Shared;
using Infrastructure.Repositories.Interfaces;
using Infrastructure.Settings;
using Microsoft.Extensions.Options;

namespace Infrastructure.Repositories.Postgres;

public class UserRepositoryPg : PgRepository, IUserRepository
{
    public UserRepositoryPg(IOptions<InfrastructureOptions> settings) : base(settings.Value)
    {
    }

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
