using AutoMapper;
using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;
using dc_rest.Exceptions;
using dc_rest.Models;
using dc_rest.Repositories.Interfaces;
using dc_rest.Services;
using dc_rest.Services.Interfaces;
using FluentValidation;
using FluentValidation.Results;
using Moq;

namespace Tests.ServicesTests;

public class NoteServiceTests
{
     private readonly Mock<IMapper> _mapperMock = new();
    private readonly Mock<INoteRepository> _repositoryMock = new();
    private readonly Mock<AbstractValidator<NoteRequestDto>> _validatorMock = new();
    private readonly INoteService _noteService;

    public NoteServiceTests()
    {
        _noteService = new NoteService(_mapperMock.Object, _repositoryMock.Object, _validatorMock.Object);
    }
    
    [Fact]
    public async Task CreateAsync_InvalidArgument_ThrowsValidationException()
    {
        NoteRequestDto noteRequestDto = new();
        Note note = new();
        ValidationResult validationResult =
            new ValidationResult(new List<ValidationFailure>([new ValidationFailure()]));
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<NoteRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);
        
        Func<Task> actual = async () => await _noteService.CreateNoteAsync(noteRequestDto);
        
        await Assert.ThrowsAsync<ValidatinonException>(actual);
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<NoteRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
    }
    
    [Fact]
    public async Task CreateAsync_ValidArgument_ReturnsAddedNoteResponseDto()
    {
        NoteRequestDto noteRequestDto = new();
        NoteResponseDto noteResponseDto = new();
        Note note = new();
        ValidationResult validationResult = new();
        _mapperMock.Setup(mapper => mapper.Map<Note>(It.IsAny<NoteRequestDto>())).Returns(note);
        _mapperMock.Setup(mapper => mapper.Map<NoteResponseDto>(It.IsAny<Note>())).Returns(noteResponseDto);
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<NoteRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);
        _repositoryMock.Setup(repository => repository.CreateAsync(It.IsAny<Note>())).ReturnsAsync(note);

        var result = await _noteService.CreateNoteAsync(noteRequestDto);
        Assert.Equal(noteResponseDto, result);
        _mapperMock.Verify(mapper => mapper.Map<Note>(It.IsAny<NoteRequestDto>()), Times.Once);
        _mapperMock.Verify(mapper => mapper.Map<NoteResponseDto>(It.IsAny<Note>()), Times.Once);
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<NoteRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
        _repositoryMock.Verify(repository => repository.CreateAsync(It.IsAny<Note>()), Times.Once);
    }

    [Fact]
    public async Task GetByIdAsync_NoteNotExist_ThrowsResourceNotFoundException()
    {
        _repositoryMock.Setup(repository => repository.GetByIdAsync(It.IsAny<long>()))
            .ThrowsAsync(new NotFoundException());

        Func<Task> actual = async () => await _noteService.GetNoteByIdAsync(-1);
        
        await Assert.ThrowsAsync<NotFoundException>(actual);
        _repositoryMock.Verify(repository => repository.GetByIdAsync(It.IsAny<long>()), Times.Once);
    }

    [Fact]
    public async Task GetByIdAsync_NoteExist_ReturnsExistingResult()
    {
        Note note = new Note();
        NoteResponseDto noteResponseDto = new();
        _repositoryMock.Setup(repository => repository.GetByIdAsync(It.IsAny<long>()))
            .ReturnsAsync(note);
        _mapperMock.Setup(mapper => mapper.Map<NoteResponseDto>(It.IsAny<Note>())).Returns(noteResponseDto);

        var result = await _noteService.GetNoteByIdAsync(1);
        
        Assert.Equal(noteResponseDto, result);
        _repositoryMock.Verify(repository => repository.GetByIdAsync(It.IsAny<long>()), Times.Once);
    }

    [Fact]
    public async Task GetAllAsync_EmptyRepository_ReturnsEmptyList()
    {
        NoteResponseDto noteResponseDto = new();
        _repositoryMock.Setup(repository => repository.GetAllAsync())
            .ReturnsAsync([]);
        _mapperMock.Setup(mapper => mapper.Map<NoteResponseDto>(It.IsAny<Note>())).Returns(noteResponseDto);

        var result = await _noteService.GetNotesAsync();
        Assert.Empty(result);
        _repositoryMock.Verify(repository => repository.GetAllAsync(), Times.Once);
        _mapperMock.Verify(mapper => mapper.Map<NoteResponseDto>(It.IsAny<Note>()), Times.Never);
    }

    [Fact]
    public async Task UpdateAsync_InvalidArgument_ThrowsValidationException()
    {
        NoteRequestDto noteRequestDto = new();
        Note note = new();
        ValidationResult validationResult =
            new ValidationResult(new List<ValidationFailure>([new ValidationFailure()]));
        _mapperMock.Setup(mapper => mapper.Map<Note>(It.IsAny<NoteRequestDto>())).Returns(note);
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<NoteRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);

        Func<Task> actual = async () => await _noteService.UpdateNoteAsync(noteRequestDto);

        await Assert.ThrowsAsync<ValidatinonException>(actual);
        
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<NoteRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
    }

    [Fact]
    public async Task UpdateAsync_ValidArgument_ReturnsAddedNoteResponseDto()
    {
        NoteRequestDto noteRequestDto = new();
        NoteResponseDto noteResponseDto = new();
        Note note = new();
        ValidationResult validationResult = new();
        _mapperMock.Setup(mapper => mapper.Map<Note>(It.IsAny<NoteRequestDto>())).Returns(note);
        _mapperMock.Setup(mapper => mapper.Map<NoteResponseDto>(It.IsAny<Note>())).Returns(noteResponseDto);
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<NoteRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);
        _repositoryMock.Setup(repository => repository.UpdateAsync(It.IsAny<Note>()))
            .ReturnsAsync(note);

        var result = await _noteService.UpdateNoteAsync(noteRequestDto);
        
        Assert.Equal(noteResponseDto, result);
        
        _mapperMock.Verify(mapper => mapper.Map<Note>(It.IsAny<NoteRequestDto>()), Times.Once);
        _mapperMock.Verify(mapper => mapper.Map<NoteResponseDto>(It.IsAny<Note>()), Times.Once);
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<NoteRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
        _repositoryMock.Verify(repository => repository.UpdateAsync(It.IsAny<Note>()), Times.Once);
    }

    [Fact]
    public async Task Delete_NoteNotExist_ThrowsResourceNotFoundException()
    {
        _repositoryMock.Setup(repository => repository.DeleteAsync(It.IsAny<long>()))
            .ThrowsAsync(new NotFoundException());

        Func<Task> actual = async () => await _noteService.DeleteNoteAsync(-1);

        await Assert.ThrowsAsync<NotFoundException>(actual);
        _repositoryMock.Verify(repository => repository.DeleteAsync(It.IsAny<long>()), Times.Once);
    }

    [Fact]
    public async Task Delete_NoteExist_NoteNoLongerExist()
    {
        _repositoryMock.Setup(repository => repository.DeleteAsync(It.IsAny<long>())).ReturnsAsync(true);

        var task =  _noteService.DeleteNoteAsync(1);
        
        await task;
        Assert.True(task.IsCompletedSuccessfully);
        _repositoryMock.Verify(repository => repository.DeleteAsync(It.IsAny<long>()), Times.Once);
    }
}