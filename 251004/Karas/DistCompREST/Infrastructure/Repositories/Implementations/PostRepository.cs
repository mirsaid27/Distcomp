using Application.Contracts.RepositoryContracts;
using Domain.Entities;

namespace Infrastructure.Repositories.Implementations;

public class PostRepository : RepositoryBase<Post>, IPostRepository
{
    public PostRepository(RepositoryContext context) 
        : base(context)
    {
    }   
}