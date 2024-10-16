using Task310.Models;
using Task310.Repositories.Interfaces;

namespace Task310.Repositories.Implementations
{
    public class InMemoryNewsRepository : BaseInMemoryRepository<News>, INewsRepository
    {

    }
}
