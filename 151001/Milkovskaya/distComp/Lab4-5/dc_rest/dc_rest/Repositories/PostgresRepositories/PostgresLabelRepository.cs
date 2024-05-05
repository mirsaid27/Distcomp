using dc_rest.Data;
using dc_rest.Exceptions;
using dc_rest.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;
using Label = dc_rest.Models.Label;

namespace dc_rest.Repositories.PostgresRepositories;

public class PostgresLabelRepository : ILabelRepository
{
    private readonly AppDbContext _dbContext;
    public PostgresLabelRepository(AppDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public async Task<IEnumerable<Label>> GetAllAsync()
        {
            return await _dbContext.Labels.AsNoTracking().ToListAsync();
        }

        public async Task<Label?> GetByIdAsync(long id)
        {
            return await _dbContext.Labels.AsNoTracking().FirstOrDefaultAsync(label => label.Id == id);
        }

        public async Task<Label> CreateAsync(Label entity)
        {
            try
            {
                await _dbContext.Labels.AddAsync(entity);
                await _dbContext.SaveChangesAsync();
                _dbContext.Entry(entity).State = EntityState.Detached; 
                
            }
            catch (DbUpdateException ex)
            {
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23505" })
                {
                    throw new UniqueConstraintException("Violation of unique constraint rule for Label", 40301);
                }
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23503" })
                {
                    throw new ForeignKeyViolation("Foreign key violation for Label", 40302);
                }
            }
            return entity;
        }

        public async Task<Label?> UpdateAsync(Label entity)
        {
            try
            {
                var existingLabel = await _dbContext.Labels.FindAsync(entity.Id);
                if (existingLabel != null)
                {
                    _dbContext.Entry(existingLabel).CurrentValues.SetValues(entity);
                    await _dbContext.SaveChangesAsync();
                    _dbContext.Entry(existingLabel).State = EntityState.Detached; 
                    return existingLabel;
                }
            }
            catch (DbUpdateException ex)
            {
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23505" })
                {
                    throw new UniqueConstraintException("Violation of unique constraint rule for Label", 40303);
                }
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23503" })
                {
                    throw new ForeignKeyViolation("Foreign key violation for Label", 40304);
                }
            }
            return null;
        }

        public async Task<bool> DeleteAsync(long id)
        {
            var existingLabel = await _dbContext.Labels.FindAsync(id);
            if (existingLabel != null)
            {
                _dbContext.Labels.Remove(existingLabel);
                await _dbContext.SaveChangesAsync();
                _dbContext.Entry(existingLabel).State = EntityState.Detached; 
                return true;
            }
            return false;
        }
}