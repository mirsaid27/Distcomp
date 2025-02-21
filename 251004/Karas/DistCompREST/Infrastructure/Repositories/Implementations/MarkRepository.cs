using Application.Contracts.RepositoryContracts;
using Domain.Entities;

namespace Infrastructure.Repositories.Implementations;

public class MarkRepository : RepositoryBase<Mark>, IMarkRepository
{
    public MarkRepository(RepositoryContext context) : base(context)
    {
    }   
}