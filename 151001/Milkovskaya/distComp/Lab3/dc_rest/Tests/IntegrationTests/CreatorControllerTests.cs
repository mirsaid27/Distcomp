using dc_rest.Controllers;
using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;
using dc_rest.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;
using Moq;

namespace Tests.IntegrationTests;

public class CreatorControllerTests
{
    [Fact]
    public Task GetAll_ShouldReturnAllEntities()
    {
        var creatorServiceMock = new Mock<ICreatorService>();
        var responses = new List<CreatorResponseDto>
        {
            new CreatorResponseDto() { Id = 1, Firstname = "Name 1", Lastname = "Lastname 1", Login = "login 1", Password = "password 1"},
            new CreatorResponseDto() { Id = 2, Firstname = "Name 2", Lastname = "Lastname 2", Login = "login 2", Password = "password 2" }
        };
        creatorServiceMock.Setup(service => service.GetCreatorsAsync()).ReturnsAsync(responses);

        var controller = new CreatorController(creatorServiceMock.Object);
        var result = controller.GetCreators();
        
        // Act
        var okResult = Xunit.Assert.IsType<OkObjectResult>(result.Result);
        var model = Xunit.Assert.IsAssignableFrom<IEnumerable<CreatorResponseDto>>(okResult.Value);
        Xunit.Assert.Equal(responses, model);
        return Task.CompletedTask;
    }
    
    
    [Fact]
    public Task GetAll_ShouldReturn_NoEntities()
    {
        var empty = new List<CreatorResponseDto>();
        var creatorServiceMock = new Mock<ICreatorService>();
        creatorServiceMock.Setup(service => service.GetCreatorsAsync()).ReturnsAsync(new List<CreatorResponseDto>());

        var controller = new CreatorController(creatorServiceMock.Object);
        var result = controller.GetCreators();

        var okResult = Xunit.Assert.IsType<OkObjectResult>(result.Result);
        var model = Xunit.Assert.IsAssignableFrom<IEnumerable<CreatorResponseDto>>(okResult.Value);
        Xunit.Assert.Equal(empty,model);
        return Task.CompletedTask;
    }

    [Fact]
    public Task GetById_ShouldReturn_Creator()
    {
        var creator = new CreatorResponseDto() { Id = 1,Firstname = "Name 1" };
        var creatorServiceMock = new Mock<ICreatorService>();
        creatorServiceMock.Setup(service => service.GetCreatorByIdAsync(1)).ReturnsAsync(creator);

        var controller = new CreatorController(creatorServiceMock.Object);
        var result = controller.GetCreatorById(1);
        
        var okResult = Xunit.Assert.IsType<OkObjectResult>(result.Result);
        var model = Xunit.Assert.IsAssignableFrom<CreatorResponseDto>(okResult.Value);
        Xunit.Assert.Equal(creator,model);
        return Task.CompletedTask;
    }
    
    
    [Fact]
    public async Task CreateCreator_WithValidModel_ReturnsCreatedAtAction()
    {
        // Arrange
        var creatorRequest = new CreatorRequestDto { Id = 1, Firstname = "abcd"};
        var creatorAdded = new CreatorResponseDto { Id = 1, Firstname = "abcd" };

        var creatorServiceMock = new Mock<ICreatorService>();
        creatorServiceMock.Setup(service => service.CreateCreatorAsync(creatorRequest)).ReturnsAsync(creatorAdded);

        var controller = new CreatorController(creatorServiceMock.Object);

        // Act
        IActionResult result = await controller.CreateCreator(creatorRequest);

        // Assert
        Xunit.Assert.NotNull(result);

        //var actionResult = result. as CreatedAtActionResult;
        /*Xunit.Assert.Equal(nameof(CreatorController.GetById), actionResult.ActionName);
        Xunit.Assert.Equal(creatorAdded, actionResult.Value);*/
    }
    
    [Fact]
    public async Task UpdateCreator_WithValidModel_ReturnsOk()
    {
        // Arrange
        var creatorRequest = new CreatorRequestDto { Id = 1, Firstname = "abcd" };
        var creatorUpdated = new CreatorResponseDto { Id = 1, Firstname = "abcd" };

        var creatorServiceMock = new Mock<ICreatorService>();
        creatorServiceMock.Setup(service => service.UpdateCreatorAsync(It.IsAny<CreatorRequestDto>())).ReturnsAsync(creatorUpdated);

        var controller = new CreatorController(creatorServiceMock.Object);

        // Act
        var result = await controller.UpdateCreator(creatorRequest);
        Xunit.Assert.NotNull(result);
        // Assert
    }

    [Fact]
    public async Task UpdateCreator_WithInvalidModel_ReturnsBadRequest()
    {
        // Arrange
        var userRequest = new CreatorRequestDto { Id = 1, Firstname = "a" };

        var controller = new CreatorController(Mock.Of<ICreatorService>());
        controller.ModelState.AddModelError("Login", "Error message");

        // Act
        var result = await controller.UpdateCreator(userRequest) ;
        Xunit.Assert.NotNull(result);
        // Assert
    }
    
    [Fact]
    public async Task DeleteCreator_WithValidId_ReturnsNoContent()
    {
        // Arrange
        int creatorId = 1;

        var creatorServiceMock = new Mock<ICreatorService>();
        creatorServiceMock.Setup(service => service.DeleteCreatorAsync(creatorId)).Verifiable();

        var controller = new CreatorController(creatorServiceMock.Object);

        // Act
        var result = await controller.DeleteCreator(creatorId);
        Xunit.Assert.NotNull(result);
        // Assert

        creatorServiceMock.Verify(); 
    }
}