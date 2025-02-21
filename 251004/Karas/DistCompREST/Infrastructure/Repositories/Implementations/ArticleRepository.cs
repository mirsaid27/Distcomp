using Application.Contracts.RepositoryContracts;
using Domain.Entities;

namespace Infrastructure.Repositories.Implementations;

public class ArticleRepository : RepositoryBase<Article>, IArticleRepository
{
    public ArticleRepository(RepositoryContext context) : base(context)
    {
    }   
}