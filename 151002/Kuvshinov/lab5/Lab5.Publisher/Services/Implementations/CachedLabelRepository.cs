using Lab5.Publisher.Repositories.Interfaces;
using Lab5.Publisher.Models;

namespace Lab5.Publisher.Services.Implementations;

public class CachedStickerRepository : IStickerRepository
{
    public Task<List<Sticker>> GetAllAsync()
    {
        throw new NotImplementedException();
    }

    public Task<Sticker?> GetByIdAsync(long id)
    {
        throw new NotImplementedException();
    }

    public Task<Sticker> CreateAsync(Sticker StickerModel)
    {
        throw new NotImplementedException();
    }

    public Task<Sticker?> UpdateAsync(long id, Sticker updatedSticker)
    {
        throw new NotImplementedException();
    }

    public Task<Sticker?> DeleteAsync(long id)
    {
        throw new NotImplementedException();
    }

    Task<IEnumerable<Sticker>> IBaseRepository<Sticker>.GetAllAsync()
    {
        throw new NotImplementedException();
    }

    public Task<Sticker?> UpdateAsync(Sticker entity)
    {
        throw new NotImplementedException();
    }

    Task<bool> IBaseRepository<Sticker>.DeleteAsync(long id)
    {
        throw new NotImplementedException();
    }
}