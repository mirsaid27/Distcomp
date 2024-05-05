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

public class NewsServiceTests
{
     private readonly Mock<IMapper> _mapperMock = new();
    private readonly Mock<INewsRepository> _repositoryMock = new();
    private readonly Mock<AbstractValidator<NewsRequestDto>> _validatorMock = new();
    private readonly INewsService _newsService;

    public NewsServiceTests()
    {
        _newsService = new NewsService(_mapperMock.Object, _repositoryMock.Object, _validatorMock.Object);
    }
    
    [Fact]
    public async Task CreateAsync_InvalidArgument_ThrowsValidationException()
    {
        NewsRequestDto newsRequestDto = new();
        News news = new();
        ValidationResult validationResult =
            new ValidationResult(new List<ValidationFailure>([new ValidationFailure()]));
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<NewsRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);
        
        Func<Task> actual = async () => await _newsService.CreateNewsAsync(newsRequestDto);
        
        await Assert.ThrowsAsync<ValidatinonException>(actual);
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<NewsRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
    }
    
    [Fact]
    public async Task CreateAsync_ValidArgument_ReturnsAddedNewsResponseDto()
    {
        NewsRequestDto newsRequestDto = new();
        NewsResponseDto newsResponseDto = new();
        News news = new();
        ValidationResult validationResult = new();
        _mapperMock.Setup(mapper => mapper.Map<News>(It.IsAny<NewsRequestDto>())).Returns(news);
        _mapperMock.Setup(mapper => mapper.Map<NewsResponseDto>(It.IsAny<News>())).Returns(newsResponseDto);
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<NewsRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);
        _repositoryMock.Setup(repository => repository.CreateAsync(It.IsAny<News>())).ReturnsAsync(news);

        var result = await _newsService.CreateNewsAsync(newsRequestDto);
        Assert.Equal(newsResponseDto, result);
        _mapperMock.Verify(mapper => mapper.Map<News>(It.IsAny<NewsRequestDto>()), Times.Once);
        _mapperMock.Verify(mapper => mapper.Map<NewsResponseDto>(It.IsAny<News>()), Times.Once);
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<NewsRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
        _repositoryMock.Verify(repository => repository.CreateAsync(It.IsAny<News>()), Times.Once);
    }

    [Fact]
    public async Task GetByIdAsync_NewsNotExist_ThrowsResourceNotFoundException()
    {
        _repositoryMock.Setup(repository => repository.GetByIdAsync(It.IsAny<long>()))
            .ThrowsAsync(new NotFoundException());

        Func<Task> actual = async () => await _newsService.GetNewsByIdAsync(-1);
        
        await Assert.ThrowsAsync<NotFoundException>(actual);
        _repositoryMock.Verify(repository => repository.GetByIdAsync(It.IsAny<long>()), Times.Once);
    }

    [Fact]
    public async Task GetByIdAsync_NewsExist_ReturnsExistingResult()
    {
        News news = new News();
        NewsResponseDto newsResponseDto = new();
        _repositoryMock.Setup(repository => repository.GetByIdAsync(It.IsAny<long>()))
            .ReturnsAsync(news);
        _mapperMock.Setup(mapper => mapper.Map<NewsResponseDto>(It.IsAny<News>())).Returns(newsResponseDto);

        var result = await _newsService.GetNewsByIdAsync(1);
        
        Assert.Equal(newsResponseDto, result);
        _repositoryMock.Verify(repository => repository.GetByIdAsync(It.IsAny<long>()), Times.Once);
    }

    [Fact]
    public async Task GetAllAsync_EmptyRepository_ReturnsEmptyList()
    {
        NewsResponseDto newsResponseDto = new();
        _repositoryMock.Setup(repository => repository.GetAllAsync())
            .ReturnsAsync([]);
        _mapperMock.Setup(mapper => mapper.Map<NewsResponseDto>(It.IsAny<News>())).Returns(newsResponseDto);

        var result = await _newsService.GetNewsAsync();
        Assert.Empty(result);
        _repositoryMock.Verify(repository => repository.GetAllAsync(), Times.Once);
        _mapperMock.Verify(mapper => mapper.Map<NewsResponseDto>(It.IsAny<News>()), Times.Never);
    }

    [Fact]
    public async Task UpdateAsync_InvalidArgument_ThrowsValidationException()
    {
        NewsRequestDto newsRequestDto = new();
        News news = new();
        ValidationResult validationResult =
            new ValidationResult(new List<ValidationFailure>([new ValidationFailure()]));
        _mapperMock.Setup(mapper => mapper.Map<News>(It.IsAny<NewsRequestDto>())).Returns(news);
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<NewsRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);

        Func<Task> actual = async () => await _newsService.UpdateNewsAsync(newsRequestDto);

        await Assert.ThrowsAsync<ValidatinonException>(actual);
        
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<NewsRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
    }

    [Fact]
    public async Task UpdateAsync_ValidArgument_ReturnsAddedNewsResponseDto()
    {
        NewsRequestDto newsRequestDto = new();
        NewsResponseDto newsResponseDto = new();
        News news = new();
        ValidationResult validationResult = new();
        _mapperMock.Setup(mapper => mapper.Map<News>(It.IsAny<NewsRequestDto>())).Returns(news);
        _mapperMock.Setup(mapper => mapper.Map<NewsResponseDto>(It.IsAny<News>())).Returns(newsResponseDto);
        _validatorMock.Setup(validator =>
                validator.ValidateAsync(It.IsAny<ValidationContext<NewsRequestDto>>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(validationResult);
        _repositoryMock.Setup(repository => repository.UpdateAsync(It.IsAny<News>()))
            .ReturnsAsync(news);

        var result = await _newsService.UpdateNewsAsync(newsRequestDto);
        
        Assert.Equal(newsResponseDto, result);
        
        _mapperMock.Verify(mapper => mapper.Map<News>(It.IsAny<NewsRequestDto>()), Times.Once);
        _mapperMock.Verify(mapper => mapper.Map<NewsResponseDto>(It.IsAny<News>()), Times.Once);
        _validatorMock.Verify(
            validator => validator.ValidateAsync(It.IsAny<ValidationContext<NewsRequestDto>>(), It.IsAny<CancellationToken>()),
            Times.Once);
        _repositoryMock.Verify(repository => repository.UpdateAsync(It.IsAny<News>()), Times.Once);
    }

    [Fact]
    public async Task Delete_NewsNotExist_ThrowsResourceNotFoundException()
    {
        _repositoryMock.Setup(repository => repository.DeleteAsync(It.IsAny<long>()))
            .ThrowsAsync(new NotFoundException());

        Func<Task> actual = async () => await _newsService.DeleteNewsAsync(-1);

        await Assert.ThrowsAsync<NotFoundException>(actual);
        _repositoryMock.Verify(repository => repository.DeleteAsync(It.IsAny<long>()), Times.Once);
    }

    [Fact]
    public async Task Delete_NewsExist_NewsNoLongerExist()
    {
        _repositoryMock.Setup(repository => repository.DeleteAsync(It.IsAny<long>())).ReturnsAsync(true);

        var task =  _newsService.DeleteNewsAsync(1);
        
        await task;
        Assert.True(task.IsCompletedSuccessfully);
        _repositoryMock.Verify(repository => repository.DeleteAsync(It.IsAny<long>()), Times.Once);
    }
}