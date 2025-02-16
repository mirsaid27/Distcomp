using System;
using Domain.Models;
using Domain.Repositories;
using Domain.Shared;
using Infrastructure.Repositories.Interfaces;

namespace Infrastructure.Repositories.Postgres;

public class MarkerRepositoryPg : IPgRepository, IMarkerRepository
{
    public Task<Result<MarkerModel>> CreateMarker(MarkerModel marker)
    {
        throw new NotImplementedException();
    }

    public Task<Result> DeleteMarker(long id)
    {
        throw new NotImplementedException();
    }

    public Task<Result<MarkerModel>> GetMarkerById(long id)
    {
        throw new NotImplementedException();
    }

    public Task<Result<IEnumerable<MarkerModel>>> GetMarkers()
    {
        throw new NotImplementedException();
    }

    public Task<Result<MarkerModel>> UpdateMarker(MarkerModel marker)
    {
        throw new NotImplementedException();
    }
}
