using Publisher.Data;
using Publisher.Models;
using Publisher.Repositories.Interfaces;

namespace Publisher.Repositories.Implementations;

public class MarkRepository : BaseRepository<Mark>, IMarkRepository
{
    public MarkRepository(AppDbContext context) : base(context)
    {
    }
}