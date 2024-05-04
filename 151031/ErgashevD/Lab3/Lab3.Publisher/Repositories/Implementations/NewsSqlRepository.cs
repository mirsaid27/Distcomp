using Lab3.Publisher.Data;
using Lab3.Publisher.Models;
using Lab3.Publisher.Repositories.Interfaces;

namespace Lab3.Publisher.Repositories.Implementations;

public class NewsSqlRepository(AppDbContext context) : BaseSqlRepository<News>(context), INewsRepository
{
}