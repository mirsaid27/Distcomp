using dc_rest.Data;
using dc_rest.Exceptions;
using dc_rest.Models;
using dc_rest.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace dc_rest.Repositories.PostgresRepositories;

public class PostgresNewsRepository : INewsRepository
{
    private readonly AppDbContext _dbContext;
    public PostgresNewsRepository(AppDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public async Task<IEnumerable<News>> GetAllAsync()
        {
            return await _dbContext.News.AsNoTracking().ToListAsync();
        }

        public async Task<News?> GetByIdAsync(long id)
        {
            return await _dbContext.News.AsNoTracking().FirstOrDefaultAsync(news => news.Id == id);
        }

        public async Task<News> CreateAsync(News entity)
        {
            try
            {
                await _dbContext.News.AddAsync(entity);
                await _dbContext.SaveChangesAsync();
                _dbContext.Entry(entity).State = EntityState.Detached; 
                
            }
            catch (DbUpdateException ex)
            {
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23505" })
                {
                    throw new UniqueConstraintException("Violation of unique constraint rule for News", 40301);
                }
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23503" })
                {
                    throw new ForeignKeyViolation("Foreign key violation for News", 40302);
                }
            }
            return entity;
        }

        public async Task<News?> UpdateAsync(News entity)
        {
            try
            {
                var existingNews = await _dbContext.News.FindAsync(entity.Id);
                if (existingNews != null)
                {
                    _dbContext.Entry(existingNews).CurrentValues.SetValues(entity);
                    await _dbContext.SaveChangesAsync();
                    _dbContext.Entry(existingNews).State = EntityState.Detached; 
                    return existingNews;
                }
            }
            catch (DbUpdateException ex)
            {
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23505" })
                {
                    throw new UniqueConstraintException("Violation of unique constraint rule for News", 40303);
                }
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23503" })
                {
                    throw new ForeignKeyViolation("Foreign key violation for News", 40304);
                }
            }
            return null;
        }

        public async Task<bool> DeleteAsync(long id)
        {
            var existingNews = await _dbContext.News.FindAsync(id);
            if (existingNews != null)
            {
                _dbContext.News.Remove(existingNews);
                await _dbContext.SaveChangesAsync();
                _dbContext.Entry(existingNews).State = EntityState.Detached; 
                return true;
            }
            return false;
        }
}