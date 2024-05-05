using dc_rest.Models;
using dc_rest.Repositories.InMemoryRepositories;

namespace Tests.RepositoriesTests;

public class LabelRepositoryTests
{
     public async Task<InMemoryLabelRepository> GetRepository()
    {
        InMemoryLabelRepository labelRepository = new InMemoryLabelRepository();
        Label label = new Label()
        {
            Name = "name"
        };

        await labelRepository.CreateAsync(label);
        return labelRepository;
    }
    
    [Fact]
    public async Task CreateAsync_ValidEntity_ReturnsEntity()
    {
        InMemoryLabelRepository labelRepository = new InMemoryLabelRepository();
        Label label = new Label()
        {
            Name = "name"
        };

        var addedLabel = await labelRepository.CreateAsync(label);
        Assert.Equal(0, addedLabel.Id);
        Assert.Equal(label.Name, addedLabel.Name);
    }

    [Fact]
    public async Task UpdateAsync_ValidEntity_ReturnsEntity()
    {
        InMemoryLabelRepository labelRepository = await GetRepository();
        Label label = new Label()
        {
            Id = 0,
            Name = "updName"
        };

        var up = await labelRepository.UpdateAsync(label);
        
        Assert.Equal(label.Name, up.Name);
    }
    
    [Fact]
    public async Task UpdateAsync_EntityNotExists_ReturnsNull()
    {
        InMemoryLabelRepository labelRepository = await GetRepository();
        Label label = new Label()
        {
            Id = 2,
            Name = "name",
        };
        
        var up = await labelRepository.UpdateAsync(label);
        
        Assert.Null(up);
    }
    
    [Fact]
    public async Task DeleteAsync_ValidId_ReturnsTrue()
    {
        InMemoryLabelRepository labelRepository = await GetRepository();
        var res = await labelRepository.DeleteAsync(0);
        Assert.True(res);
    }
    
    [Fact]
    public async Task DeleteAsync_NotValidId_ReturnsTrue()
    {
        InMemoryLabelRepository labelRepository = await GetRepository();
        var res = await labelRepository.DeleteAsync(10);
        Assert.False(res);
    }
    
    [Fact]
    public async Task GetByIdAsync_NotValidId_ReturnsNull()
    {
        InMemoryLabelRepository labelRepository = await GetRepository();
        var resLabel = await labelRepository.GetByIdAsync(10);
        Assert.Null(resLabel);
        
    }
    
    [Fact]
    public async Task GetByIdAsync_ValidId_ReturnsEntity()
    {
        InMemoryLabelRepository labelRepository = await GetRepository();
        var resLabel = await labelRepository.GetByIdAsync(0);
        Assert.NotNull(resLabel);
        Assert.Equal("name", resLabel.Name);
    }
}