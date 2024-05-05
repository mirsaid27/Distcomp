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

public class LabelServiceTests
{
     private readonly Mock<IMapper> _mapperMock = new();
    private readonly Mock<ILabelRepository> _repositoryMock = new();
    private readonly Mock<AbstractValidator<LabelRequestDto>> _validatorMock = new();
    private readonly ILabelService _labelService;

    public LabelServiceTests()
    {
        _labelService = new LabelService(_mapperMock.Object, _repositoryMock.Object, _validatorMock.Object);
    }
    
    [Fact]
    public async Task CreateAsync_InvalidArgument_ThrowsValidationException()
    {
        LabelRequestDto labelRequestDto = new();
        Label label = new();
        ValidationResult validationResult =
            new ValidationResult(new List<ValidationFailure>([new ValidationFailure()]));
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<LabelRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);
        
        Func<Task> actual = async () => await _labelService.CreateLabelAsync(labelRequestDto);
        
        await Assert.ThrowsAsync<ValidatinonException>(actual);
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<LabelRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
    }
    
    [Fact]
    public async Task CreateAsync_ValidArgument_ReturnsAddedLabelResponseDto()
    {
        LabelRequestDto labelRequestDto = new();
        LabelResponseDto labelResponseDto = new();
        Label label = new();
        ValidationResult validationResult = new();
        _mapperMock.Setup(mapper => mapper.Map<Label>(It.IsAny<LabelRequestDto>())).Returns(label);
        _mapperMock.Setup(mapper => mapper.Map<LabelResponseDto>(It.IsAny<Label>())).Returns(labelResponseDto);
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<LabelRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);
        _repositoryMock.Setup(repository => repository.CreateAsync(It.IsAny<Label>())).ReturnsAsync(label);

        var result = await _labelService.CreateLabelAsync(labelRequestDto);
        Assert.Equal(labelResponseDto, result);
        _mapperMock.Verify(mapper => mapper.Map<Label>(It.IsAny<LabelRequestDto>()), Times.Once);
        _mapperMock.Verify(mapper => mapper.Map<LabelResponseDto>(It.IsAny<Label>()), Times.Once);
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<LabelRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
        _repositoryMock.Verify(repository => repository.CreateAsync(It.IsAny<Label>()), Times.Once);
    }

    [Fact]
    public async Task GetByIdAsync_LabelNotExist_ThrowsResourceNotFoundException()
    {
        _repositoryMock.Setup(repository => repository.GetByIdAsync(It.IsAny<long>()))
            .ThrowsAsync(new NotFoundException());

        Func<Task> actual = async () => await _labelService.GetLabelByIdAsync(-1);
        
        await Assert.ThrowsAsync<NotFoundException>(actual);
        _repositoryMock.Verify(repository => repository.GetByIdAsync(It.IsAny<long>()), Times.Once);
    }

    [Fact]
    public async Task GetByIdAsync_LabelExist_ReturnsExistingResult()
    {
        Label label = new Label();
        LabelResponseDto labelResponseDto = new();
        _repositoryMock.Setup(repository => repository.GetByIdAsync(It.IsAny<long>()))
            .ReturnsAsync(label);
        _mapperMock.Setup(mapper => mapper.Map<LabelResponseDto>(It.IsAny<Label>())).Returns(labelResponseDto);

        var result = await _labelService.GetLabelByIdAsync(1);
        
        Assert.Equal(labelResponseDto, result);
        _repositoryMock.Verify(repository => repository.GetByIdAsync(It.IsAny<long>()), Times.Once);
    }

    [Fact]
    public async Task GetAllAsync_EmptyRepository_ReturnsEmptyList()
    {
        LabelResponseDto labelResponseDto = new();
        _repositoryMock.Setup(repository => repository.GetAllAsync())
            .ReturnsAsync([]);
        _mapperMock.Setup(mapper => mapper.Map<LabelResponseDto>(It.IsAny<Label>())).Returns(labelResponseDto);

        var result = await _labelService.GetLabelsAsync();
        Assert.Empty(result);
        _repositoryMock.Verify(repository => repository.GetAllAsync(), Times.Once);
        _mapperMock.Verify(mapper => mapper.Map<LabelResponseDto>(It.IsAny<Label>()), Times.Never);
    }

    [Fact]
    public async Task UpdateAsync_InvalidArgument_ThrowsValidationException()
    {
        LabelRequestDto labelRequestDto = new();
        Label label = new();
        ValidationResult validationResult =
            new ValidationResult(new List<ValidationFailure>([new ValidationFailure()]));
        _mapperMock.Setup(mapper => mapper.Map<Label>(It.IsAny<LabelRequestDto>())).Returns(label);
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<LabelRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);

        Func<Task> actual = async () => await _labelService.UpdateLabelAsync(labelRequestDto);

        await Assert.ThrowsAsync<ValidatinonException>(actual);
        
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<LabelRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
    }

    [Fact]
    public async Task UpdateAsync_ValidArgument_ReturnsAddedLabelResponseDto()
    {
        LabelRequestDto labelRequestDto = new();
        LabelResponseDto labelResponseDto = new();
        Label label = new();
        ValidationResult validationResult = new();
        _mapperMock.Setup(mapper => mapper.Map<Label>(It.IsAny<LabelRequestDto>())).Returns(label);
        _mapperMock.Setup(mapper => mapper.Map<LabelResponseDto>(It.IsAny<Label>())).Returns(labelResponseDto);
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<LabelRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);
        _repositoryMock.Setup(repository => repository.UpdateAsync(It.IsAny<Label>()))
            .ReturnsAsync(label);

        var result = await _labelService.UpdateLabelAsync(labelRequestDto);
        
        Assert.Equal(labelResponseDto, result);
        
        _mapperMock.Verify(mapper => mapper.Map<Label>(It.IsAny<LabelRequestDto>()), Times.Once);
        _mapperMock.Verify(mapper => mapper.Map<LabelResponseDto>(It.IsAny<Label>()), Times.Once);
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<LabelRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
        _repositoryMock.Verify(repository => repository.UpdateAsync(It.IsAny<Label>()), Times.Once);
    }

    [Fact]
    public async Task Delete_LabelNotExist_ThrowsResourceNotFoundException()
    {
        _repositoryMock.Setup(repository => repository.DeleteAsync(It.IsAny<long>()))
            .ThrowsAsync(new NotFoundException());

        Func<Task> actual = async () => await _labelService.DeleteLabelAsync(-1);

        await Assert.ThrowsAsync<NotFoundException>(actual);
        _repositoryMock.Verify(repository => repository.DeleteAsync(It.IsAny<long>()), Times.Once);
    }

    [Fact]
    public async Task Delete_LabelExist_LabelNoLongerExist()
    {
        _repositoryMock.Setup(repository => repository.DeleteAsync(It.IsAny<long>())).ReturnsAsync(true);

        var task =  _labelService.DeleteLabelAsync(1);
        
        await task;
        Assert.True(task.IsCompletedSuccessfully);
        _repositoryMock.Verify(repository => repository.DeleteAsync(It.IsAny<long>()), Times.Once);
    }
}