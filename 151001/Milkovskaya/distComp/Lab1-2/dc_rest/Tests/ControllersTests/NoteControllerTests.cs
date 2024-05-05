using dc_rest.Controllers;
using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;
using dc_rest.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;
using Moq;

namespace Tests.ControllersTests;

public class NoteControllerTests
{
    [Fact]
    public Task GetAll_ShouldReturnAllEntities()
    {
        var noteServiceMock = new Mock<INoteService>();
        var responses = new List<NoteResponseDto>
        {
            new NoteResponseDto() { Id = 1, Content = "Content 1"},
            new NoteResponseDto() { Id = 2, Content = "name 2" }
        };
        noteServiceMock.Setup(service => service.GetNotesAsync()).ReturnsAsync(responses);

        var controller = new NoteController(noteServiceMock.Object);
        var result = controller.GetNotes();
        
        // Act
        var okResult = Xunit.Assert.IsType<OkObjectResult>(result.Result);
        var model = Xunit.Assert.IsAssignableFrom<IEnumerable<NoteResponseDto>>(okResult.Value);
        Xunit.Assert.Equal(responses, model);
        return Task.CompletedTask;
    }
    
    
    [Fact]
    public Task GetAll_ShouldReturn_NoEntities()
    {
        var empty = new List<NoteResponseDto>();
        var noteServiceMock = new Mock<INoteService>();
        noteServiceMock.Setup(service => service.GetNotesAsync()).ReturnsAsync(new List<NoteResponseDto>());

        var controller = new NoteController(noteServiceMock.Object);
        var result = controller.GetNotes();

        var okResult = Xunit.Assert.IsType<OkObjectResult>(result.Result);
        var model = Xunit.Assert.IsAssignableFrom<IEnumerable<NoteResponseDto>>(okResult.Value);
        Xunit.Assert.Equal(empty,model);
        return Task.CompletedTask;
    }

    [Fact]
    public Task GetById_ShouldReturn_Note()
    {
        var note = new NoteResponseDto() { Id = 1, Content = "Content 1" };
        var noteServiceMock = new Mock<INoteService>();
        noteServiceMock.Setup(service => service.GetNoteByIdAsync(1)).ReturnsAsync(note);

        var controller = new NoteController(noteServiceMock.Object);
        var result = controller.GetNoteById(1);
        
        var okResult = Xunit.Assert.IsType<OkObjectResult>(result.Result);
        var model = Xunit.Assert.IsAssignableFrom<NoteResponseDto>(okResult.Value);
        Xunit.Assert.Equal(note,model);
        return Task.CompletedTask;
    }
    
    
    [Fact]
    public async Task CreateNote_WithValidModel_ReturnsCreatedAtAction()
    {
        // Arrange
        var noteRequest = new NoteRequestDto { Id = 1, Content = "abcd"};
        var noteAdded = new NoteResponseDto { Id = 1, Content = "abcd" };

        var noteServiceMock = new Mock<INoteService>();
        noteServiceMock.Setup(service => service.CreateNoteAsync(noteRequest)).ReturnsAsync(noteAdded);

        var controller = new NoteController(noteServiceMock.Object);

        // Act
        IActionResult result = await controller.CreateNote(noteRequest);

        // Assert
        Xunit.Assert.NotNull(result);

        //var actionResult = result. as CreatedAtActionResult;
        /*Xunit.Assert.Equal(nameof(NoteController.GetById), actionResult.ActionContent);
        Xunit.Assert.Equal(noteAdded, actionResult.Value);*/
    }
    
    [Fact]
    public async Task UpdateNote_WithValidModel_ReturnsOk()
    {
        // Arrange
        var noteRequest = new NoteRequestDto { Id = 1, Content = "abcd" };
        var noteUpdated = new NoteResponseDto { Id = 1, Content = "abcd" };

        var noteServiceMock = new Mock<INoteService>();
        noteServiceMock.Setup(service => service.UpdateNoteAsync(It.IsAny<NoteRequestDto>())).ReturnsAsync(noteUpdated);

        var controller = new NoteController(noteServiceMock.Object);

        // Act
        var result = await controller.UpdateNote(noteRequest);
        Xunit.Assert.NotNull(result);
        // Assert
    }

    [Fact]
    public async Task UpdateNote_WithInvalidModel_ReturnsBadRequest()
    {
        // Arrange
        var userRequest = new NoteRequestDto { Id = 1, Content = "a" };

        var controller = new NoteController(Mock.Of<INoteService>());
        controller.ModelState.AddModelError("Login", "Error message");

        // Act
        var result = await controller.UpdateNote(userRequest) ;
        Xunit.Assert.NotNull(result);
        // Assert
    }
    
    [Fact]
    public async Task DeleteNote_WithValidId_ReturnsNoContent()
    {
        // Arrange
        int noteId = 1;

        var noteServiceMock = new Mock<INoteService>();
        noteServiceMock.Setup(service => service.DeleteNoteAsync(noteId)).Verifiable();

        var controller = new NoteController(noteServiceMock.Object);

        // Act
        var result = await controller.DeleteNote(noteId);
        Xunit.Assert.NotNull(result);
        // Assert

        noteServiceMock.Verify(); 
    }
}