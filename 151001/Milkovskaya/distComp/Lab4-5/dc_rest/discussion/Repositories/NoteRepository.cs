using Cassandra.Mapping;
using dc_rest.Exceptions;
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
    public async Task<Note> GetByIdAsync(NoteKey id)
    {
        Note? result = (await GetAllByKeyAsync(id)).FirstOrDefault();

        if (result is null)
        {
            throw new NotFoundException("not found",40401);
        }

        return result;
    }

    public async Task<Note?> CreateAsync(Note entity)
    {
        ArgumentNullException.ThrowIfNull(entity);

        await _mapper.InsertAsync(entity);

        return entity;
    }

    public async Task<Note> UpdateAsync(NoteKey id, Note entity)
    {
        ArgumentNullException.ThrowIfNull(entity);

        Note? result = (await GetAllByKeyAsync(id)).FirstOrDefault();

        if (result is null)
        {
            throw new NotFoundException("not found",40401);
        }

        result.Content = entity.Content;
        await _mapper.UpdateAsync(result);

        return result;
    }

    public async Task<bool> DeleteAsync(NoteKey id)
    {
        Note? result = (await GetAllByKeyAsync(id)).FirstOrDefault();

        if (result is null)
        {
            throw new NotFoundException("not found",40401);
        }

        await _mapper.DeleteAsync(result);
        return true;
    }

    private async Task<IEnumerable<Note>> GetAllByKeyAsync(NoteKey key)
    {
        List<string> query = [];
        List<object> args = [];
    
        if (key.Country is not null)
        {
            query.Add("country = ?");
            args.Add(key.Country);
        }
        if (key.NewsId is not null)
        {
            query.Add("newsid = ?");
            args.Add(key.NewsId);
        }
        if (key.Id is not null)
        {
            query.Add("id = ?");
            args.Add(key.Id);
        }

        return await _mapper.FetchAsync<Note>("WHERE " + string.Join(" AND ", query) + " ALLOW FILTERING",
            args.ToArray());
    }
}