using dc_rest.Models;
using dc_rest.Repositories.Interfaces;

namespace dc_rest.Repositories.InMemoryRepositories;

public class InMemoryNoteRepository : INoteRepository
{
    private Dictionary<long, Note> _notes = [];
    private long _idGlobal;
    
    public async Task<IEnumerable<Note>> GetAllAsync()
    {
        IEnumerable<Note> notes = [];
        await Task.Run(() =>
        {
            notes = _notes.Values.ToList();
        });
        return notes;
    }

    public async Task<Note?> GetByIdAsync(long id)
    {
        Note? note = null;
        await Task.Run(() =>
        {
            _notes.TryGetValue(id, out note);
        });
        return note;
    }

    public async Task<Note> CreateAsync(Note entity)
    {
        await Task.Run(() =>
        {
            entity.Id = _idGlobal;
            _notes.TryAdd(entity.Id, entity);
            long id = Interlocked.Increment(ref _idGlobal);
        });
        return entity;
    }

    public async Task<Note?> UpdateAsync(Note entity)
    {
        return await Task.Run(() =>
        {
            if (_notes.ContainsKey(entity.Id))
            {
                _notes[entity.Id] = entity;
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
            result = _notes.Remove(id);
        });
        return result;
    }
}