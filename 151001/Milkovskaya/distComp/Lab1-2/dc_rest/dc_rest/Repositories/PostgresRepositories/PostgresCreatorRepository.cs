using dc_rest.Data;
using dc_rest.Models;
using dc_rest.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;
using dc_rest.Exceptions;

namespace dc_rest.Repositories.PostgresRepositories
{
    public class PostgresCreatorRepository : ICreatorRepository
    {
        private readonly AppDbContext _dbContext;
        public PostgresCreatorRepository(AppDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<IEnumerable<Creator>> GetAllAsync()
        {
            return await _dbContext.Creators.AsNoTracking().ToListAsync();
        }

        public async Task<Creator?> GetByIdAsync(long id)
        {
            return await _dbContext.Creators.AsNoTracking().FirstOrDefaultAsync(creator => creator.Id == id);
        }

        public async Task<Creator> CreateAsync(Creator entity)
        {
            try
            {
                await _dbContext.Creators.AddAsync(entity);
                await _dbContext.SaveChangesAsync();
                _dbContext.Entry(entity).State = EntityState.Detached; 
                
            }
            catch (DbUpdateException ex)
            {
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23505" })
                {
                    throw new UniqueConstraintException("Violation of unique constraint rule for Creator", 40301);
                }
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23503" })
                {
                    throw new ForeignKeyViolation("Foreign key violation for Creator", 40302);
                }
            }
            return entity;
        }

        public async Task<Creator?> UpdateAsync(Creator entity)
        {
            try
            {
                var existingCreator = await _dbContext.Creators.FindAsync(entity.Id);
                if (existingCreator != null)
                {
                    _dbContext.Entry(existingCreator).CurrentValues.SetValues(entity);
                    await _dbContext.SaveChangesAsync();
                    _dbContext.Entry(existingCreator).State = EntityState.Detached; 
                    return existingCreator;
                }
            }
            catch (DbUpdateException ex)
            {
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23505" })
                {
                    throw new UniqueConstraintException("Violation of unique constraint rule for Creator", 40303);
                }
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23503" })
                {
                    throw new ForeignKeyViolation("Foreign key violation for Creator", 40304);
                }
            }
            return null;
        }

        public async Task<bool> DeleteAsync(long id)
        {
            var existingCreator = await _dbContext.Creators.FindAsync(id);
            if (existingCreator != null)
            {
                _dbContext.Creators.Remove(existingCreator);
                await _dbContext.SaveChangesAsync();
                _dbContext.Entry(existingCreator).State = EntityState.Detached; 
                return true;
            }
            return false;
        }
    }
}
