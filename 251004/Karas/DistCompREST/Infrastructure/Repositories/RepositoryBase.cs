using System.Linq.Expressions;
using Application.Contracts.RepositoryContracts;
using Domain.Entities;
using Microsoft.EntityFrameworkCore;

namespace Infrastructure.Repositories;

public abstract class RepositoryBase<T> : IRepositoryBase<T>
    where T : BaseModel
{
    protected readonly RepositoryContext _context;
    protected readonly DbSet<T> _dbSet;

    public RepositoryBase(RepositoryContext context)
    {
        _context = context;
        _dbSet = context.Set<T>();
    }
    
    public virtual async Task<IEnumerable<T>> GetAllAsync()
        => await _dbSet.ToListAsync();

    public virtual async Task<T?> GetByIdAsync(long id)
        => await _dbSet.FirstOrDefaultAsync(x => x.Id == id);

    public virtual async Task<T> CreateAsync(T entity)
    {
        var newEntity = await _dbSet.AddAsync(entity);
        await _context.SaveChangesAsync();
        return newEntity.Entity;
    }

    public virtual async Task<T?> UpdateAsync(T entity)
    {
        var newEntity = _dbSet.Update(entity);
        await _context.SaveChangesAsync();
        return newEntity.Entity;
    }

    public virtual async Task<bool> DeleteAsync(long id)
    {
        var entity = await GetByIdAsync(id);
        if (entity is null)
        {
            return false;
        }

        _dbSet.Remove(entity);
        await _context.SaveChangesAsync();
        return true;
    }
}