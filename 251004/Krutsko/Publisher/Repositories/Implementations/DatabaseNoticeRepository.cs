using Publisher.Data;
using Publisher.Models;
using Publisher.Repositories.Interfaces;

namespace Publisher.Repositories.Implementations;

public class DatabaseNoticeRepository : BaseDatabaseRepository<Notice>, INoticeRepository
{
    public DatabaseNoticeRepository(AppDbContext context) : base(context)
    {
    }
}