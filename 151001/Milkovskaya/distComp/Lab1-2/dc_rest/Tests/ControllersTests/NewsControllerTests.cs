using dc_rest.Controllers;
using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;
using dc_rest.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;
using Moq;

namespace Tests.ControllersTests;

public class NewsControllerTests
{
    [Fact]
    public Task GetAll_ShouldReturnAllEntities()
    {
        var newsServiceMock = new Mock<INewsService>();
        var responses = new List<NewsResponseDto>
        {
            new NewsResponseDto() { Id = 1, Title = "Title 1", Content = "Content 1", Created = DateTime.Now, Modified = DateTime.Now},
            new NewsResponseDto() { Id = 2, Title = "Title 2", Content = "Content 2", Created = DateTime.Now, Modified = DateTime.Now}
        };
        newsServiceMock.Setup(service => service.GetNewsAsync()).ReturnsAsync(responses);

        var controller = new NewsController(newsServiceMock.Object);
        var result = controller.GetNewss();
        
        // Act
        var okResult = Xunit.Assert.IsType<OkObjectResult>(result.Result);
        var model = Xunit.Assert.IsAssignableFrom<IEnumerable<NewsResponseDto>>(okResult.Value);
        Xunit.Assert.Equal(responses, model);
        return Task.CompletedTask;
    }
    
    
    [Fact]
    public Task GetAll_ShouldReturn_NoEntities()
    {
        var empty = new List<NewsResponseDto>();
        var newsServiceMock = new Mock<INewsService>();
        newsServiceMock.Setup(service => service.GetNewsAsync()).ReturnsAsync(new List<NewsResponseDto>());

        var controller = new NewsController(newsServiceMock.Object);
        var result = controller.GetNewss();

        var okResult = Xunit.Assert.IsType<OkObjectResult>(result.Result);
        var model = Xunit.Assert.IsAssignableFrom<IEnumerable<NewsResponseDto>>(okResult.Value);
        Xunit.Assert.Equal(empty,model);
        return Task.CompletedTask;
    }

    [Fact]
    public Task GetById_ShouldReturn_News()
    {
        var news = new NewsResponseDto() { Id = 1, Title = "Title 1", Content = "Content 1", Created = DateTime.Now, Modified = DateTime.Now};
        var newsServiceMock = new Mock<INewsService>();
        newsServiceMock.Setup(service => service.GetNewsByIdAsync(1)).ReturnsAsync(news);

        var controller = new NewsController(newsServiceMock.Object);
        var result = controller.GetNewsById(1);
        
        var okResult = Xunit.Assert.IsType<OkObjectResult>(result.Result);
        var model = Xunit.Assert.IsAssignableFrom<NewsResponseDto>(okResult.Value);
        Xunit.Assert.Equal(news,model);
        return Task.CompletedTask;
    }
    
    
    [Fact]
    public async Task CreateNews_WithValidModel_ReturnsCreatedAtAction()
    {
        // Arrange
        var newsRequest = new NewsRequestDto { Id = 1, Title = "Title 1", Content = "Content 1", Created = DateTime.Now, Modified = DateTime.Now};
        var newsAdded = new NewsResponseDto { Id = 1, Title = "Title 1", Content = "Content 1", Created = DateTime.Now, Modified = DateTime.Now};

        var newsServiceMock = new Mock<INewsService>();
        newsServiceMock.Setup(service => service.CreateNewsAsync(newsRequest)).ReturnsAsync(newsAdded);

        var controller = new NewsController(newsServiceMock.Object);

        // Act
        IActionResult result = await controller.CreateNews(newsRequest);

        // Assert
        Xunit.Assert.NotNull(result);

        //var actionResult = result. as CreatedAtActionResult;
        /*Xunit.Assert.Equal(nameof(NewsController.GetById), actionResult.ActionName);
        Xunit.Assert.Equal(newsAdded, actionResult.Value);*/
    }
    
    [Fact]
    public async Task UpdateNews_WithValidModel_ReturnsOk()
    {
        // Arrange
        var newsRequest = new NewsRequestDto { Id = 1, Title = "Title 1", Content = "Content 1", Created = DateTime.Now, Modified = DateTime.Now};
        var newsUpdated = new NewsResponseDto { Id = 1, Title = "Title 1", Content = "Content 1", Created = DateTime.Now, Modified = DateTime.Now};

        var newsServiceMock = new Mock<INewsService>();
        newsServiceMock.Setup(service => service.UpdateNewsAsync(It.IsAny<NewsRequestDto>())).ReturnsAsync(newsUpdated);

        var controller = new NewsController(newsServiceMock.Object);

        // Act
        var result = await controller.UpdateNews(newsRequest);
        Xunit.Assert.NotNull(result);
        // Assert
    }

    [Fact]
    public async Task UpdateNews_WithInvalidModel_ReturnsBadRequest()
    {
        // Arrange
        var userRequest = new NewsRequestDto { Id = 1, Title = "Title 1", Content = "Content 1", Created = DateTime.Now, Modified = DateTime.Now};

        var controller = new NewsController(Mock.Of<INewsService>());
        controller.ModelState.AddModelError("Login", "Error message");

        // Act
        var result = await controller.UpdateNews(userRequest) ;
        Xunit.Assert.NotNull(result);
        // Assert
    }
    
    [Fact]
    public async Task DeleteNews_WithValidId_ReturnsNoContent()
    {
        // Arrange
        int newsId = 1;

        var newsServiceMock = new Mock<INewsService>();
        newsServiceMock.Setup(service => service.DeleteNewsAsync(newsId)).Verifiable();

        var controller = new NewsController(newsServiceMock.Object);

        // Act
        var result = await controller.DeleteNews(newsId);
        Xunit.Assert.NotNull(result);
        // Assert

        newsServiceMock.Verify(); 
    }
}