using dc_rest.Models;
using dc_rest.Repositories.InMemoryRepositories;

namespace Tests.DbRepositoriesTests;

public class NoteRepositoryTests
{
     public async Task<InMemoryNoteRepository> GetRepository()
    {
        InMemoryNoteRepository noteRepository = new InMemoryNoteRepository();
        Note note = new Note()
        {
            Content = "content"
        };

        await noteRepository.CreateAsync(note);
        return noteRepository;
    }
    
    [Fact]
    public async Task CreateAsync_ValidEntity_ReturnsEntity()
    {
        InMemoryNoteRepository noteRepository = new InMemoryNoteRepository();
        Note note = new Note()
        {
            Content = "content"
        };

        var addedNote = await noteRepository.CreateAsync(note);
        Assert.Equal(0, addedNote.Id);
        Assert.Equal(note.Content, addedNote.Content);
    }

    [Fact]
    public async Task UpdateAsync_ValidEntity_ReturnsEntity()
    {
        InMemoryNoteRepository noteRepository = await GetRepository();
        Note note = new Note()
        {
            Id = 0,
            Content = "newcontent"
        };

        var up = await noteRepository.UpdateAsync(note);
        
        Assert.Equal(note.Content, up.Content);
    }
    
    [Fact]
    public async Task UpdateAsync_EntityNotExists_ReturnsNull()
    {
        InMemoryNoteRepository noteRepository = await GetRepository();
        Note note = new Note()
        {
            Id = 2,
            Content = "content"
        };
        
        var up = await noteRepository.UpdateAsync(note);
        
        Assert.Null(up);
    }
    
    [Fact]
    public async Task DeleteAsync_ValidId_ReturnsTrue()
    {
        InMemoryNoteRepository noteRepository = await GetRepository();
        var res = await noteRepository.DeleteAsync(0);
        Assert.True(res);
    }
    
    [Fact]
    public async Task DeleteAsync_NotValidId_ReturnsTrue()
    {
        InMemoryNoteRepository noteRepository = await GetRepository();
        var res = await noteRepository.DeleteAsync(10);
        Assert.False(res);
    }
    
    [Fact]
    public async Task GetByIdAsync_NotValidId_ReturnsNull()
    {
        InMemoryNoteRepository noteRepository = await GetRepository();
        var resNote = await noteRepository.GetByIdAsync(10);
        Assert.Null(resNote);
        
    }
    
    [Fact]
    public async Task GetByIdAsync_ValidId_ReturnsEntity()
    {
        InMemoryNoteRepository noteRepository = await GetRepository();
        var resNote = await noteRepository.GetByIdAsync(0);
        Assert.NotNull(resNote);
        Assert.Equal("content", resNote.Content);
    }
}