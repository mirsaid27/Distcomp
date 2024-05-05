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
    private readonly Mock<IBaseService> _baseMock = new();
    private readonly INoteService _noteService;

    public NoteServiceTests()
    {
        _noteService = new NoteService(_baseMock.Object);
    }
    
    [Fact]
    public async Task CreateAsync_InvalidArgument_ThrowsValidationException()
    {
        NoteRequestDto noteRequestDto = new();
        Note note = new();
        
        Func<Task> actual = async () => await _noteService.CreateNoteAsync(noteRequestDto);
        
        
    }
    
    [Fact]
    public async Task CreateAsync_ValidArgument_ReturnsAddedNoteResponseDto()
    {
        NoteRequestDto noteRequestDto = new();
        NoteResponseDto noteResponseDto = new();
        Note note = new();
        ValidationResult validationResult = new();
        
        
    }

    [Fact]
    public async Task GetByIdAsync_NoteNotExist_ThrowsResourceNotFoundException()
    {

        Func<Task> actual = async () => await _noteService.GetNoteByIdAsync(-1);
        
        
    }

    [Fact]
    public async Task GetByIdAsync_NoteExist_ReturnsExistingResult()
    {
        Note note = new Note();
        NoteResponseDto noteResponseDto = new();

        
        
    }

    [Fact]
    public async Task GetAllAsync_EmptyRepository_ReturnsEmptyList()
    {
        NoteResponseDto noteResponseDto = new();

        
    }

    [Fact]
    public async Task UpdateAsync_InvalidArgument_ThrowsValidationException()
    {
        NoteRequestDto noteRequestDto = new();
        Note note = new();
        ValidationResult validationResult =
            new ValidationResult(new List<ValidationFailure>([new ValidationFailure()]));


        
        
    }

    [Fact]
    public async Task UpdateAsync_ValidArgument_ReturnsAddedNoteResponseDto()
    {
        NoteRequestDto noteRequestDto = new();
        NoteResponseDto noteResponseDto = new();
        Note note = new();
        ValidationResult validationResult = new();

        
        
    }

    [Fact]
    public async Task Delete_NoteNotExist_ThrowsResourceNotFoundException()
    {

        Func<Task> actual = async () => await _noteService.DeleteNoteAsync(-1);

        
    }

    [Fact]
    public async Task Delete_NoteExist_NoteNoLongerExist()
    {

        var task =  _noteService.DeleteNoteAsync(1);
        
        await task;
        
    }
}