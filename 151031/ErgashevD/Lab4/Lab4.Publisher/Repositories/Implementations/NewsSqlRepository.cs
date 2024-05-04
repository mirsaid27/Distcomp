using Lab4.Publisher.Data;
using Lab4.Publisher.Models;
using Lab4.Publisher.Repositories.Interfaces;

namespace Lab4.Publisher.Repositories.Implementations;

public class NewsSqlRepository(AppDbContext context) : BaseSqlRepository<News>(context), INewsRepository
{
}