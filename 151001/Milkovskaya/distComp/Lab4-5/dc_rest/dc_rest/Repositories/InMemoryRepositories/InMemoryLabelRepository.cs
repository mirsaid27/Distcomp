using dc_rest.Models;
using dc_rest.Repositories.Interfaces;

namespace dc_rest.Repositories.InMemoryRepositories;

public class InMemoryLabelRepository : ILabelRepository
{
    private Dictionary<long, Label> _labels = [];
    private long _idGlobal;
    
    public async Task<IEnumerable<Label>> GetAllAsync()
    {
        IEnumerable<Label> labels = [];
        await Task.Run(() =>
        {
            labels = _labels.Values.ToList();
        });
        return labels;
    }

    public async Task<Label?> GetByIdAsync(long id)
    {
        Label? label = null;
        await Task.Run(() =>
        {
            _labels.TryGetValue(id, out label);
        });
        return label;
    }

    public async Task<Label> CreateAsync(Label entity)
    {
        await Task.Run(() =>
        {
            entity.Id = _idGlobal;
            _labels.TryAdd(entity.Id, entity);
            long id = Interlocked.Increment(ref _idGlobal);
        });
        return entity;
    }

    public async Task<Label?> UpdateAsync(Label entity)
    {
        return await Task.Run(() =>
        {
            if (_labels.ContainsKey(entity.Id))
            {
                _labels[entity.Id] = entity;
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
            result = _labels.Remove(id);
        });
        return result;
    }
}