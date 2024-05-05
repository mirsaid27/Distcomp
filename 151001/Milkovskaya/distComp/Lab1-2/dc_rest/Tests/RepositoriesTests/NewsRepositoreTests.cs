using dc_rest.Models;
using dc_rest.Repositories.InMemoryRepositories;

namespace Tests.RepositoriesTests;

public class NewsRepositoreTests
{
     public async Task<InMemoryNewsRepository> GetRepository()
    {
        InMemoryNewsRepository newsRepository = new InMemoryNewsRepository();
        News news = new News()
        {
            Title = "title",
            Content = "content",
            Created = new DateTime(1,1,1),
            Modified = new DateTime(1,1,1),
        };

        await newsRepository.CreateAsync(news);
        return newsRepository;
    }
    
    [Fact]
    public async Task CreateAsync_ValidEntity_ReturnsEntity()
    {
        InMemoryNewsRepository newsRepository = new InMemoryNewsRepository();
        News news = new News()
        {
            Title = "title",
            Content = "content",
            Created = new DateTime(1,1,1),
            Modified = new DateTime(1,1,1),
        };

        var addedNews = await newsRepository.CreateAsync(news);
        Assert.Equal(0, addedNews.Id);
        Assert.Equal(news.Title, addedNews.Title);
    }

    [Fact]
    public async Task UpdateAsync_ValidEntity_ReturnsEntity()
    {
        InMemoryNewsRepository newsRepository = await GetRepository();
        News news = new News()
        {
            Id = 0,
            Title = "updtitle",
            Content = "content",
            Created = new DateTime(1,1,1),
            Modified = new DateTime(1,1,1),
        };

        var up = await newsRepository.UpdateAsync(news);
        
        Assert.Equal(news.Title, up.Title);
    }
    
    [Fact]
    public async Task UpdateAsync_EntityNotExists_ReturnsNull()
    {
        InMemoryNewsRepository newsRepository = await GetRepository();
        News news = new News()
        {
            Id = 2,
            Title = "title",
            Content = "content",
            Created = new DateTime(1,1,1),
            Modified = new DateTime(1,1,1),
        };
        
        var up = await newsRepository.UpdateAsync(news);
        
        Assert.Null(up);
    }
    
    [Fact]
    public async Task DeleteAsync_ValidId_ReturnsTrue()
    {
        InMemoryNewsRepository newsRepository = await GetRepository();
        var res = await newsRepository.DeleteAsync(0);
        Assert.True(res);
    }
    
    [Fact]
    public async Task DeleteAsync_NotValidId_ReturnsTrue()
    {
        InMemoryNewsRepository newsRepository = await GetRepository();
        var res = await newsRepository.DeleteAsync(10);
        Assert.False(res);
    }
    
    [Fact]
    public async Task GetByIdAsync_NotValidId_ReturnsNull()
    {
        InMemoryNewsRepository newsRepository = await GetRepository();
        var resNews = await newsRepository.GetByIdAsync(10);
        Assert.Null(resNews);
        
    }
    
    [Fact]
    public async Task GetByIdAsync_ValidId_ReturnsEntity()
    {
        InMemoryNewsRepository newsRepository = await GetRepository();
        var resNews = await newsRepository.GetByIdAsync(0);
        Assert.NotNull(resNews);
        Assert.Equal("title", resNews.Title);
    }
}