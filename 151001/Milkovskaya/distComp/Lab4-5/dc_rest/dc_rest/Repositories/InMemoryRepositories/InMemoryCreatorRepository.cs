using dc_rest.Models;
using dc_rest.Repositories.Interfaces;

namespace dc_rest.Repositories.InMemoryRepositories;

public class InMemoryCreatorRepository : ICreatorRepository
{
    private Dictionary<long, Creator> _creators = [];
    private long _idGlobal;
    
    public async Task<IEnumerable<Creator>> GetAllAsync()
    {
        IEnumerable<Creator> creators = [];
        await Task.Run(() =>
        {
            creators = _creators.Values.ToList();
        });
        return creators;
    }

    public async Task<Creator?> GetByIdAsync(long id)
    {
        Creator? creator = null;
        await Task.Run(() =>
        {
             _creators.TryGetValue(id, out creator);
        });
        return creator;
    }

    public async Task<Creator> CreateAsync(Creator entity)
    {
        await Task.Run(() =>
        {
            entity.Id = _idGlobal;
            _creators.TryAdd(entity.Id, entity);
            long id = Interlocked.Increment(ref _idGlobal);
        });
        return entity;
    }

    public async Task<Creator?> UpdateAsync(Creator entity)
    {
        return await Task.Run(() =>
        {
            if (_creators.ContainsKey(entity.Id))
            {
                _creators[entity.Id] = entity;
                return entity;
            }
            return null;
        });
    }

    public async Task<bool> DeleteAsync(long id)
    {
        bool result = true;
        await Task.Run(() =>
        {
            result = _creators.Remove(id);
        });
        return result;
    }
}