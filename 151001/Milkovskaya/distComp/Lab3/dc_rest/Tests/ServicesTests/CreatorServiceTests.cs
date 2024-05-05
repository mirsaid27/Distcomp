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

public class CreatorServiceTests
{
    private readonly Mock<IMapper> _mapperMock = new();
    private readonly Mock<ICreatorRepository> _repositoryMock = new();
    private readonly Mock<AbstractValidator<CreatorRequestDto>> _validatorMock = new();
    private readonly ICreatorService _creatorService;

    public CreatorServiceTests()
    {
        _creatorService = new CreatorService(_mapperMock.Object, _repositoryMock.Object, _validatorMock.Object);
    }
    
    [Fact]
    public async Task CreateAsync_InvalidArgument_ThrowsValidationException()
    {
        CreatorRequestDto creatorRequestDto = new();
        Creator creator = new();
        ValidationResult validationResult =
            new ValidationResult(new List<ValidationFailure>([new ValidationFailure()]));
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<CreatorRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);
        
        Func<Task> actual = async () => await _creatorService.CreateCreatorAsync(creatorRequestDto);
        
        await Assert.ThrowsAsync<ValidatinonException>(actual);
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<CreatorRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
    }
    
    [Fact]
    public async Task CreateAsync_ValidArgument_ReturnsAddedCreatorResponseDto()
    {
        CreatorRequestDto creatorRequestDto = new();
        CreatorResponseDto creatorResponseDto = new();
        Creator creator = new();
        ValidationResult validationResult = new();
        _mapperMock.Setup(mapper => mapper.Map<Creator>(It.IsAny<CreatorRequestDto>())).Returns(creator);
        _mapperMock.Setup(mapper => mapper.Map<CreatorResponseDto>(It.IsAny<Creator>())).Returns(creatorResponseDto);
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<CreatorRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);
        _repositoryMock.Setup(repository => repository.CreateAsync(It.IsAny<Creator>())).ReturnsAsync(creator);

        var result = await _creatorService.CreateCreatorAsync(creatorRequestDto);
        Assert.Equal(creatorResponseDto, result);
        _mapperMock.Verify(mapper => mapper.Map<Creator>(It.IsAny<CreatorRequestDto>()), Times.Once);
        _mapperMock.Verify(mapper => mapper.Map<CreatorResponseDto>(It.IsAny<Creator>()), Times.Once);
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<CreatorRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
        _repositoryMock.Verify(repository => repository.CreateAsync(It.IsAny<Creator>()), Times.Once);
    }

    [Fact]
    public async Task GetByIdAsync_CreatorNotExist_ThrowsResourceNotFoundException()
    {
        _repositoryMock.Setup(repository => repository.GetByIdAsync(It.IsAny<long>()))
            .ThrowsAsync(new NotFoundException());

        Func<Task> actual = async () => await _creatorService.GetCreatorByIdAsync(-1);
        
        await Assert.ThrowsAsync<NotFoundException>(actual);
        _repositoryMock.Verify(repository => repository.GetByIdAsync(It.IsAny<long>()), Times.Once);
    }

    [Fact]
    public async Task GetByIdAsync_CreatorExist_ReturnsExistingResult()
    {
        Creator creator = new Creator();
        CreatorResponseDto creatorResponseDto = new();
        _repositoryMock.Setup(repository => repository.GetByIdAsync(It.IsAny<long>()))
            .ReturnsAsync(creator);
        _mapperMock.Setup(mapper => mapper.Map<CreatorResponseDto>(It.IsAny<Creator>())).Returns(creatorResponseDto);

        var result = await _creatorService.GetCreatorByIdAsync(1);
        
        Assert.Equal(creatorResponseDto, result);
        _repositoryMock.Verify(repository => repository.GetByIdAsync(It.IsAny<long>()), Times.Once);
    }

    [Fact]
    public async Task GetAllAsync_EmptyRepository_ReturnsEmptyList()
    {
        CreatorResponseDto creatorResponseDto = new();
        _repositoryMock.Setup(repository => repository.GetAllAsync())
            .ReturnsAsync([]);
        _mapperMock.Setup(mapper => mapper.Map<CreatorResponseDto>(It.IsAny<Creator>())).Returns(creatorResponseDto);

        var result = await _creatorService.GetCreatorsAsync();
        Assert.Empty(result);
        _repositoryMock.Verify(repository => repository.GetAllAsync(), Times.Once);
        _mapperMock.Verify(mapper => mapper.Map<CreatorResponseDto>(It.IsAny<Creator>()), Times.Never);
    }

    [Fact]
    public async Task UpdateAsync_InvalidArgument_ThrowsValidationException()
    {
        CreatorRequestDto creatorRequestDto = new();
        Creator creator = new();
        ValidationResult validationResult =
            new ValidationResult(new List<ValidationFailure>([new ValidationFailure()]));
        _mapperMock.Setup(mapper => mapper.Map<Creator>(It.IsAny<CreatorRequestDto>())).Returns(creator);
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<CreatorRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);

        Func<Task> actual = async () => await _creatorService.UpdateCreatorAsync(creatorRequestDto);

        await Assert.ThrowsAsync<ValidatinonException>(actual);
        
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<CreatorRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
    }

    [Fact]
    public async Task UpdateAsync_ValidArgument_ReturnsAddedCreatorResponseDto()
    {
        CreatorRequestDto creatorRequestDto = new();
        CreatorResponseDto creatorResponseDto = new();
        Creator creator = new();
        ValidationResult validationResult = new();
        _mapperMock.Setup(mapper => mapper.Map<Creator>(It.IsAny<CreatorRequestDto>())).Returns(creator);
        _mapperMock.Setup(mapper => mapper.Map<CreatorResponseDto>(It.IsAny<Creator>())).Returns(creatorResponseDto);
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<CreatorRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);
        _repositoryMock.Setup(repository => repository.UpdateAsync(It.IsAny<Creator>()))
            .ReturnsAsync(creator);

        var result = await _creatorService.UpdateCreatorAsync(creatorRequestDto);
        
        Assert.Equal(creatorResponseDto, result);
        
        _mapperMock.Verify(mapper => mapper.Map<Creator>(It.IsAny<CreatorRequestDto>()), Times.Once);
        _mapperMock.Verify(mapper => mapper.Map<CreatorResponseDto>(It.IsAny<Creator>()), Times.Once);
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<CreatorRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
        _repositoryMock.Verify(repository => repository.UpdateAsync(It.IsAny<Creator>()), Times.Once);
    }

    [Fact]
    public async Task Delete_CreatorNotExist_ThrowsResourceNotFoundException()
    {
        _repositoryMock.Setup(repository => repository.DeleteAsync(It.IsAny<long>()))
            .ThrowsAsync(new NotFoundException());

        Func<Task> actual = async () => await _creatorService.DeleteCreatorAsync(-1);

        await Assert.ThrowsAsync<NotFoundException>(actual);
        _repositoryMock.Verify(repository => repository.DeleteAsync(It.IsAny<long>()), Times.Once);
    }

    [Fact]
    public async Task Delete_CreatorExist_CreatorNoLongerExist()
    {
        _repositoryMock.Setup(repository => repository.DeleteAsync(It.IsAny<long>())).ReturnsAsync(true);

        var task =  _creatorService.DeleteCreatorAsync(1);
        
        await task;
        Assert.True(task.IsCompletedSuccessfully);
        _repositoryMock.Verify(repository => repository.DeleteAsync(It.IsAny<long>()), Times.Once);
    }
}