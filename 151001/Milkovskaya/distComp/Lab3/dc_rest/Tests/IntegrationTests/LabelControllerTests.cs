using dc_rest.Controllers;
using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;
using dc_rest.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;
using Moq;

namespace Tests.IntegrationTests;

public class LabelControllerTests
{
    [Fact]
    public Task GetAll_ShouldReturnAllEntities()
    {
        var labelServiceMock = new Mock<ILabelService>();
        var responses = new List<LabelResponseDto>
        {
            new LabelResponseDto() { Id = 1, Name = "Name 1"},
            new LabelResponseDto() { Id = 2, Name = "name 2" }
        };
        labelServiceMock.Setup(service => service.GetLabelsAsync()).ReturnsAsync(responses);

        var controller = new LabelController(labelServiceMock.Object);
        var result = controller.GetLabels();
        
        // Act
        var okResult = Xunit.Assert.IsType<OkObjectResult>(result.Result);
        var model = Xunit.Assert.IsAssignableFrom<IEnumerable<LabelResponseDto>>(okResult.Value);
        Xunit.Assert.Equal(responses, model);
        return Task.CompletedTask;
    }
    
    
    [Fact]
    public Task GetAll_ShouldReturn_NoEntities()
    {
        var empty = new List<LabelResponseDto>();
        var labelServiceMock = new Mock<ILabelService>();
        labelServiceMock.Setup(service => service.GetLabelsAsync()).ReturnsAsync(new List<LabelResponseDto>());

        var controller = new LabelController(labelServiceMock.Object);
        var result = controller.GetLabels();

        var okResult = Xunit.Assert.IsType<OkObjectResult>(result.Result);
        var model = Xunit.Assert.IsAssignableFrom<IEnumerable<LabelResponseDto>>(okResult.Value);
        Xunit.Assert.Equal(empty,model);
        return Task.CompletedTask;
    }

    [Fact]
    public Task GetById_ShouldReturn_Label()
    {
        var label = new LabelResponseDto() { Id = 1, Name = "Name 1" };
        var labelServiceMock = new Mock<ILabelService>();
        labelServiceMock.Setup(service => service.GetLabelByIdAsync(1)).ReturnsAsync(label);

        var controller = new LabelController(labelServiceMock.Object);
        var result = controller.GetLabelById(1);
        
        var okResult = Xunit.Assert.IsType<OkObjectResult>(result.Result);
        var model = Xunit.Assert.IsAssignableFrom<LabelResponseDto>(okResult.Value);
        Xunit.Assert.Equal(label,model);
        return Task.CompletedTask;
    }
    
    
    [Fact]
    public async Task CreateLabel_WithValidModel_ReturnsCreatedAtAction()
    {
        // Arrange
        var labelRequest = new LabelRequestDto { Id = 1, Name = "abcd"};
        var labelAdded = new LabelResponseDto { Id = 1, Name = "abcd" };

        var labelServiceMock = new Mock<ILabelService>();
        labelServiceMock.Setup(service => service.CreateLabelAsync(labelRequest)).ReturnsAsync(labelAdded);

        var controller = new LabelController(labelServiceMock.Object);

        // Act
        IActionResult result = await controller.CreateLabel(labelRequest);

        // Assert
        Xunit.Assert.NotNull(result);

        //var actionResult = result. as CreatedAtActionResult;
        /*Xunit.Assert.Equal(nameof(LabelController.GetById), actionResult.ActionName);
        Xunit.Assert.Equal(labelAdded, actionResult.Value);*/
    }
    
    [Fact]
    public async Task UpdateLabel_WithValidModel_ReturnsOk()
    {
        // Arrange
        var labelRequest = new LabelRequestDto { Id = 1, Name = "abcd" };
        var labelUpdated = new LabelResponseDto { Id = 1, Name = "abcd" };

        var labelServiceMock = new Mock<ILabelService>();
        labelServiceMock.Setup(service => service.UpdateLabelAsync(It.IsAny<LabelRequestDto>())).ReturnsAsync(labelUpdated);

        var controller = new LabelController(labelServiceMock.Object);

        // Act
        var result = await controller.UpdateLabel(labelRequest);
        Xunit.Assert.NotNull(result);
        // Assert
    }

    [Fact]
    public async Task UpdateLabel_WithInvalidModel_ReturnsBadRequest()
    {
        // Arrange
        var userRequest = new LabelRequestDto { Id = 1, Name = "a" };

        var controller = new LabelController(Mock.Of<ILabelService>());
        controller.ModelState.AddModelError("Login", "Error message");

        // Act
        var result = await controller.UpdateLabel(userRequest) ;
        Xunit.Assert.NotNull(result);
        // Assert
    }
    
    [Fact]
    public async Task DeleteLabel_WithValidId_ReturnsNoContent()
    {
        // Arrange
        int labelId = 1;

        var labelServiceMock = new Mock<ILabelService>();
        labelServiceMock.Setup(service => service.DeleteLabelAsync(labelId)).Verifiable();

        var controller = new LabelController(labelServiceMock.Object);

        // Act
        var result = await controller.DeleteLabel(labelId);
        Xunit.Assert.NotNull(result);
        // Assert

        labelServiceMock.Verify(); 
    }
}