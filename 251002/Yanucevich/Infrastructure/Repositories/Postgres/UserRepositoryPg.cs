using System;
using Domain.Models;
using Domain.Repositories;
using Domain.Shared;
using Infrastructure.Repositories.Interfaces;
using Infrastructure.Settings;
using Microsoft.Extensions.Options;
using Npgsql;

namespace Infrastructure.Repositories.Postgres;

public class UserRepositoryPg : PgRepository, IUserRepository
{
    public UserRepositoryPg(IOptions<InfrastructureOptions> settings) : base(settings.Value)
    {
    }

    public async Task<Result<UserModel>> CreateUser(UserModel user)
    {
        const string sqlCreateUser = 
        """
           INSERT 
             INTO tbl_user
                  (
                    login
                  , password
                  , firstname
                  , lastname
                  )
           VALUES (
                    @Login
                  , @Password
                  , @Firstname
                  , @Lastname
                  )
        RETURNING id
        """;

        await using var connection = await GetConnection();
        await using var cmd = new NpgsqlCommand(sqlCreateUser, connection);

        return null;
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
