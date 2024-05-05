using discussion.Models;

namespace discussion.Repositories.Interfaces;

public interface INoteRepository
{
    Task<IEnumerable<Note>> GetAllAsync();

    Task<Note?> GetByIdAsync(long id);

    Task<Note?> CreateAsync(Note entity);

    Task<Note?> UpdateAsync(Note entity);

    Task<bool> DeleteAsync(long id);
}