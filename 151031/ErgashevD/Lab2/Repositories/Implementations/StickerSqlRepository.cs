using Lab2.Data;
using Lab2.Models;
using Lab2.Repositories.Interfaces;

namespace Lab2.Repositories.Implementations
{
    public class StickerSqlRepository(AppDbContext context) : BaseSqlRepository<Sticker>(context), IStickerRepository
    {

    }
}
