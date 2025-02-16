using System;
using Dapper;
using Domain.Models;
using Domain.Repositories;
using Domain.Shared;
using Infrastructure.Repositories.Interfaces;
using Infrastructure.Settings;
using Microsoft.Extensions.Options;
using Npgsql;

namespace Infrastructure.Repositories.Postgres;

public class MarkerRepositoryPg : PgRepository, IMarkerRepository
{
    public MarkerRepositoryPg(IOptions<InfrastructureOptions> settings) : base(settings.Value)
    {
    }

    public async Task<Result<MarkerModel>> CreateMarker(MarkerModel marker)
    {
        const string sqlInsertMarker =
        """
           INSERT 
             INTO tbl_marker
                  (
                    name
                  )
           VALUES (@Name)
        RETURNING id
        """;

        await using var connection = await GetConnection();
        long markerId = await connection.QuerySingleAsync<long>(
            new CommandDefinition(
                sqlInsertMarker,
                marker
            )
        );

        return new MarkerModel{ Id = markerId, Name = marker.Name };
    }

    public async Task<Result> DeleteMarker(long id)
    {
        const string sqlDeleteMarker = 
        """
        DELETE
          FROM tbl_marker
         WHERE id = @Id
        """;

        await using var connection = await GetConnection();

        using var command = new NpgsqlCommand(sqlDeleteMarker, connection);
        command.Parameters.AddWithValue("Id", id);

        var result = await command.ExecuteNonQueryAsync();

        return result == 0 ? Result.Failure(Error.NullValue) : Result.Success();
    }

    public async Task<Result<MarkerModel>> GetMarkerById(long id)
    {
        const string sqlGetMarkerById =
        """
        SELECT name
             , id
          FROM tbl_marker
         WHERE id = @Id
        """;

        await using var connection = await GetConnection();
        MarkerModel? result = await connection.QuerySingleOrDefaultAsync<MarkerModel>(
            new CommandDefinition(
                sqlGetMarkerById,
                new {
                    Id = id
                }
            )
        );

        return result;
    }

    public async Task<Result<IEnumerable<MarkerModel>>> GetMarkers()
    {
        const string sqlGetMarkers = 
        """
        SELECT name
             , id
          FROM tbl_marker
        """;

        await using var connection = await GetConnection();
        IEnumerable<MarkerModel> result = await connection.QueryAsync<MarkerModel>(
            new CommandDefinition(
                sqlGetMarkers
            )
        );

        return Result.Success(result);
    }

    public async Task<Result<MarkerModel>> UpdateMarker(MarkerModel marker)
    {
        const string sqlUpdateMarker = 
        """
           UPDATE tbl_marker
              SET name = @Name
            WHERE id = @Id
        RETURNING id
        """;

        await using var connection = await GetConnection();

        long id = await connection.QuerySingleOrDefaultAsync<long>(
            new CommandDefinition(
                sqlUpdateMarker,
                marker
            )
        );

        if (id==marker.Id){
            return marker;
        }

        return Result.Failure<MarkerModel>(Error.NullValue);
    }
}
