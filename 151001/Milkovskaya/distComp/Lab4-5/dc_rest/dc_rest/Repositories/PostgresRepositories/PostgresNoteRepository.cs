using dc_rest.Data;
using dc_rest.Exceptions;
using dc_rest.Models;
using dc_rest.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace dc_rest.Repositories.PostgresRepositories;

public class PostgresNoteRepository : INoteRepository
{
    private readonly AppDbContext _dbContext;
    public PostgresNoteRepository(AppDbContext dbContext)
    {
        _dbContext = dbContext;
    }

   public async Task<IEnumerable<Note>> GetAllAsync()
        { 
            return await _dbContext.Notes.AsNoTracking().ToListAsync();
        }

        public async Task<Note?> GetByIdAsync(long id)
        {
            return await _dbContext.Notes.AsNoTracking().FirstOrDefaultAsync(note => note.Id == id);
        }

        public async Task<Note> CreateAsync(Note entity)
        {
            try
            {
                await _dbContext.Notes.AddAsync(entity);
                await _dbContext.SaveChangesAsync();
                _dbContext.Entry(entity).State = EntityState.Detached; 
                
            }
            catch (DbUpdateException ex)
            {
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23505" })
                {
                    throw new UniqueConstraintException("Violation of unique constraint rule for Note", 40301);
                }
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23503" })
                {
                    throw new ForeignKeyViolation("Foreign key violation for Note", 40302);
                }
            }
            return entity;
        }

        public async Task<Note?> UpdateAsync(Note entity)
        {
            try
            {
                var existingNote = await _dbContext.Notes.FindAsync(entity.Id);
                if (existingNote != null)
                {
                    _dbContext.Entry(existingNote).CurrentValues.SetValues(entity);
                    await _dbContext.SaveChangesAsync();
                    _dbContext.Entry(existingNote).State = EntityState.Detached; 
                    return existingNote;
                }
            }
            catch (DbUpdateException ex)
            {
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23505" })
                {
                    throw new UniqueConstraintException("Violation of unique constraint rule for Note", 40303);
                }
                if (ex.InnerException is Npgsql.PostgresException { SqlState: "23503" })
                {
                    throw new ForeignKeyViolation("Foreign key violation for Note", 40304);
                }
            }
            return null;
        }

        public async Task<bool> DeleteAsync(long id)
        {
            var existingNote = await _dbContext.Notes.FindAsync(id);
            if (existingNote != null)
            {
                _dbContext.Notes.Remove(existingNote);
                await _dbContext.SaveChangesAsync();
                _dbContext.Entry(existingNote).State = EntityState.Detached; 
                return true;
            }
            return false;
        }
}