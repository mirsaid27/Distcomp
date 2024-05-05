using Lab2.Data;
using Lab2.Models;
using Lab2.Repositories.Interfaces;

namespace Lab2.Repositories.Implementations
{
    public class NewsSqlRepository(AppDbContext context) : BaseSqlRepository<News>(context), INewsRepository
    {

    }
}
