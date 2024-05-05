using Lab2.Data;
using Lab2.Models;
using Lab2.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace Lab2.Repositories.Implementations
{
    public abstract class BaseSqlRepository<DbModel> : IBaseRepository<DbModel>
        where DbModel : BaseModel

    {
        protected readonly AppDbContext _context;
        protected readonly DbSet<DbModel> _dbSet;

        public BaseSqlRepository(AppDbContext context)
        {
            _context = context;
            _dbSet = context.Set<DbModel>();
        }

        public virtual async Task<DbModel?> GetByIdAsync(long id)
            => await _dbSet.FirstOrDefaultAsync(x => x.Id == id);

        public virtual async Task<IEnumerable<DbModel>> GetAllAsync()
            => await _dbSet.ToListAsync();

        public virtual async Task<DbModel> CreateAsync(DbModel model)
        {
            var newModel = await _dbSet.AddAsync(model);
            await _context.SaveChangesAsync();
            return newModel.Entity;
        }

        public virtual async Task<DbModel?> UpdateAsync(DbModel model)
        {
            var newModel = _context.Update(model);
            await _context.SaveChangesAsync();
            return newModel.Entity;
        }

        public virtual async Task<bool> DeleteAsync(long id)
        {
            var model = await GetByIdAsync(id);
            if (model is null)
            {
                return false;
            }
            _dbSet.Remove(model);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}
