using dc_rest.Models;
using dc_rest.Repositories.Interfaces;

namespace dc_rest.Repositories.InMemoryRepositories;

public class InMemoryNewsRepository : INewsRepository
{
    private Dictionary<long, News> _news = [];
    private long _idGlobal;
    
    public async Task<IEnumerable<News>> GetAllAsync()
    {
        IEnumerable<News> news = [];
        await Task.Run(() =>
        {
            news = _news.Values.ToList();
        });
        return news;
    }

    public async Task<News?> GetByIdAsync(long id)
    {
        News? news = null;
        await Task.Run(() =>
        {
            _news.TryGetValue(id, out news);
        });
        return news;
    }

    public async Task<News> CreateAsync(News entity)
    {
        await Task.Run(() =>
        {
            entity.Id = _idGlobal;
            _news.TryAdd(entity.Id, entity);
            long id = Interlocked.Increment(ref _idGlobal);
        });
        return entity;
    }

    public async Task<News?> UpdateAsync(News entity)
    {
        return await Task.Run(() =>
        {
            if (_news.ContainsKey(entity.Id))
            {
                _news[entity.Id] = entity;
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
            result = _news.Remove(id);
        });
        return result;
    }
}