using System;
using Dapper;
using Domain.Models;
using Domain.Repositories;
using Domain.Shared;
using Infrastructure.Errors;
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
        await using var cmd = new NpgsqlCommand(sqlInsertMarker, connection);
        cmd.Parameters.AddWithValue("Name", marker.Name);

        using var reader = await cmd.ExecuteReaderAsync(System.Data.CommandBehavior.SingleResult);
        
        if (await reader.ReadAsync())
        {
            var markerId = reader.GetInt32(0);
            return new MarkerModel { Id = markerId, Name = marker.Name };
        }
        else
        {
            return Result.Failure<MarkerModel>(MarkerErrors.MarkerNotUniqueError);
        }
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

        return result == 0 ? Result.Failure(MarkerErrors.MarkerNotFoundError) : Result.Success();
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

        using var cmd = new NpgsqlCommand(sqlGetMarkerById, connection);
        cmd.Parameters.AddWithValue("Id", id);

        var reader = await cmd.ExecuteReaderAsync();

        if (await reader.ReadAsync())
        {
            var markerId = reader.GetInt32(reader.GetOrdinal("id"));
            var markerName = reader.GetString(reader.GetOrdinal("name"));

            return new MarkerModel { Id = markerId, Name = markerName };
        }
        else
        {
            return Result.Failure<MarkerModel>(MarkerErrors.MarkerNotFoundError);
        }
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
        using var cmd = new NpgsqlCommand(sqlGetMarkers, connection);
    
        using var reader = await cmd.ExecuteReaderAsync();
        var markers = new List<MarkerModel>();

        while (await reader.ReadAsync())
        {
            var marker = new MarkerModel
            {
                Id = reader.GetInt32(reader.GetOrdinal("id")),
                Name = reader.GetString(reader.GetOrdinal("name"))
            };

            markers.Add(marker);
        }

        return markers;
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
        using var cmd = new NpgsqlCommand(sqlUpdateMarker, connection);

        cmd.Parameters.AddWithValue("Name", marker.Name);
        cmd.Parameters.AddWithValue("Id", marker.Id);

        var returnedId = await cmd.ExecuteScalarAsync();

        if (returnedId != null && (long)returnedId == marker.Id)
        {
            return new MarkerModel{
                Id = marker.Id,
                Name = marker.Name
            };
        }

        return Result.Failure<MarkerModel>(Error.NullValue);
    }
}
