using DistComp.Models;
using DistComp.Repositories.Interfaces;

namespace DistComp.Repositories.Implementations;

public class InMemoryNoticeRepository : BaseInMemoryRepository<Notice>, INoticeRepository
{
    
}