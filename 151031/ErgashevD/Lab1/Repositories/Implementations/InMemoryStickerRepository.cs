using Lab1.Models;
using Lab1.Repositories.Interfaces;

namespace Lab1.Repositories.Implementations
{
    public class InMemoryStickerRepository : BaseInMemoryRepository<Sticker>, IStickerRepository
    {

    }
}
