using Cassandra.Mapping;
using discussion.Models;
using INoteRepository = discussion.Repositories.Interfaces.INoteRepository;

namespace discussion.Repositories;

public class NoteRepository : INoteRepository
{
    private readonly IMapper _mapper;

    public NoteRepository(IMapper mapper)
    {
        _mapper = mapper;
    }
    public async Task<IEnumerable<Note>> GetAllAsync()
    {
        return await _mapper.FetchAsync<Note>("SELECT * FROM tbl_note");
    }

    public async Task<Note?> GetByIdAsync(long id)
    {
        return await _mapper.SingleAsync<Note>("SELECT * FROM tbl_note WHERE id = ?", id);
    }

    public async Task<Note?> CreateAsync(Note entity)
    {
        await _mapper.InsertAsync(entity);
        return entity;
    }

    public async Task<Note?> UpdateAsync(Note entity)
    {
        await _mapper.UpdateAsync(entity);
        return entity;
    }

    public async Task<bool> DeleteAsync(long id)
    {
        await _mapper.DeleteAsync<Note>("WHERE id = ?", id);
        return true;
    }
}