using Lab1.Models;
using Lab1.Repositories.Interfaces;

namespace Lab1.Repositories.Implementations
{
    public abstract class BaseInMemoryRepository<TEntity> : IBaseRepository<TEntity>
        where TEntity : BaseModel

    {
        private readonly Dictionary<long, TEntity> _entities = [];
        private long _idCounter;

        public async Task<TEntity> CreateAsync(TEntity entity)
        {
            await Task.Run(() =>
            {
                var id = Interlocked.Increment(ref _idCounter);
                entity.Id = id;
                _entities.TryAdd(id, entity);
            });
            return entity;
        }

        public async Task<bool> DeleteAsync(long id)
        {
            bool result = true;
            await Task.Run(() =>
            {
                result = _entities.Remove(id);
            });
            return result;
        }

        public async Task<IEnumerable<TEntity>> GetAllAsync()
        {
            IEnumerable<TEntity> entities = [];
            await Task.Run(() =>
            {
                entities = _entities.Values.ToList();
            });
            return entities;
        }

        public async Task<TEntity?> GetByIdAsync(long id)
        {
            TEntity? result = null;
            await Task.Run(() =>
            {
                _entities.TryGetValue(id, out result);
            });
            return result;
        }

        public async Task<TEntity?> UpdateAsync(TEntity entity)
        {
            await Task.Run(() =>
            {
                if (_entities.ContainsKey(entity.Id))
                {
                    _entities[entity.Id] = entity;
                }
                else
                {
                    entity = null;
                }
            });
            return entity;
        }
    }
}
