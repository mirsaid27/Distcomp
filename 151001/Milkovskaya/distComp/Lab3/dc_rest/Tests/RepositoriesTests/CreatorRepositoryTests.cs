using dc_rest.Models;
using dc_rest.Repositories.InMemoryRepositories;

namespace Tests.RepositoriesTests;

public class CreatorRepositoryTests
{

    public async Task<InMemoryCreatorRepository> GetRepository()
    {
        InMemoryCreatorRepository creatorRepository = new InMemoryCreatorRepository();
        Creator creator = new Creator()
        {
            Firstname = "name",
            Lastname = "lastname",
            Login = "login",
            Password = "password",
        };

        await creatorRepository.CreateAsync(creator);
        return creatorRepository;
    }
    
    [Fact]
    public async Task CreateAsync_ValidEntity_ReturnsEntity()
    {
        InMemoryCreatorRepository creatorRepository = new InMemoryCreatorRepository();
        Creator creator = new Creator()
        {
            Firstname = "name",
            Lastname = "lastname",
            Login = "login",
            Password = "password",
        };

        var addedCreator = await creatorRepository.CreateAsync(creator);
        Assert.Equal(0, addedCreator.Id);
        Assert.Equal(creator.Firstname, addedCreator.Firstname);
        Assert.Equal(creator.Lastname, addedCreator.Lastname);
        Assert.Equal(creator.Login, addedCreator.Login);
        Assert.Equal(creator.Password, addedCreator.Password);
    }

    [Fact]
    public async Task UpdateAsync_ValidEntity_ReturnsEntity()
    {
        InMemoryCreatorRepository creatorRepository = await GetRepository();
        Creator creator = new Creator()
        {
            Id = 0,
            Firstname = "updName",
            Lastname = "lastname",
            Login = "login",
            Password = "password",
        };

        var up = await creatorRepository.UpdateAsync(creator);
        
        Assert.Equal(creator.Firstname, up.Firstname);
    }
    
    [Fact]
    public async Task UpdateAsync_EntityNotExists_ReturnsNull()
    {
        InMemoryCreatorRepository creatorRepository = await GetRepository();
        Creator creator = new Creator()
        {
            Id = 2,
            Firstname = "name",
            Lastname = "lastname",
            Login = "login",
            Password = "password",
        };
        
        var up = await creatorRepository.UpdateAsync(creator);
        
        Assert.Null(up);
    }
    
    [Fact]
    public async Task DeleteAsync_ValidId_ReturnsTrue()
    {
        InMemoryCreatorRepository creatorRepository = await GetRepository();
        var res = await creatorRepository.DeleteAsync(0);
        Assert.True(res);
    }
    
    [Fact]
    public async Task DeleteAsync_NotValidId_ReturnsTrue()
    {
        InMemoryCreatorRepository creatorRepository = await GetRepository();
        var res = await creatorRepository.DeleteAsync(10);
        Assert.False(res);
    }
    
    [Fact]
    public async Task GetByIdAsync_NotValidId_ReturnsNull()
    {
        InMemoryCreatorRepository creatorRepository = await GetRepository();
        var resCreator = await creatorRepository.GetByIdAsync(10);
        Assert.Null(resCreator);
        
    }
    
    [Fact]
    public async Task GetByIdAsync_ValidId_ReturnsEntity()
    {
        InMemoryCreatorRepository creatorRepository = await GetRepository();
        var resCreator = await creatorRepository.GetByIdAsync(0);
        Assert.NotNull(resCreator);
        Assert.Equal("name", resCreator.Firstname);
    }
}