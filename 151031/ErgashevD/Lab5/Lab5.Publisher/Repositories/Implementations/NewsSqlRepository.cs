using Lab5.Publisher.Data;
using Lab5.Publisher.Models;
using Lab5.Publisher.Repositories.Interfaces;

namespace Lab5.Publisher.Repositories.Implementations;

public class NewsSqlRepository(AppDbContext context) : BaseSqlRepository<News>(context), INewsRepository
{
}