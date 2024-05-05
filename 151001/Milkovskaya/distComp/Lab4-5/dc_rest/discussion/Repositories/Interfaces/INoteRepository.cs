using discussion.Models;

namespace discussion.Repositories.Interfaces;

public interface INoteRepository
{
    Task<IEnumerable<Note>> GetAllAsync();

    Task<Note> GetByIdAsync(NoteKey id);

    Task<Note?> CreateAsync(Note entity);

    Task<Note> UpdateAsync(NoteKey noteKey, Note entity);

    Task<bool> DeleteAsync(NoteKey id);
}