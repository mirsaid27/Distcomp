using AutoMapper;
using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;
using dc_rest.Exceptions;
using dc_rest.Models;
using dc_rest.Repositories.Interfaces;
using dc_rest.Services.Interfaces;
using FluentValidation;

namespace dc_rest.Services;

public class NewsService : INewsService
{
    private IMapper _mapper;
    private INewsRepository _repository;
    private AbstractValidator<NewsRequestDto> _validation;
    public NewsService(IMapper mapper, INewsRepository repository, AbstractValidator<NewsRequestDto> validator)
    {
        _mapper = mapper;
        _repository = repository;
        _validation = validator;
    }
    public async Task<IEnumerable<NewsResponseDto>> GetNewsAsync()
    {
        var news = await _repository.GetAllAsync();
        return _mapper.Map<IEnumerable<NewsResponseDto>>(news);
    }

    public async Task<NewsResponseDto> GetNewsByIdAsync(long id)
    {
        var news = await _repository.GetByIdAsync(id);
        if (news == null)
        {
            throw new NotFoundException($"News with {id} id was not found", 400401);
        }
        return _mapper.Map<NewsResponseDto>(news);
    }

    public async Task<NewsResponseDto> CreateNewsAsync(NewsRequestDto news)
    {
        var result = await _validation.ValidateAsync(news);
        if (!result.IsValid)
        {
            throw new ValidatinonException("Invalid data for news", 40001);
        }
        var newsReturned = await _repository.CreateAsync(_mapper.Map<News>(news));
        return _mapper.Map<NewsResponseDto>(newsReturned);
    }

    public async Task<NewsResponseDto> UpdateNewsAsync(NewsRequestDto news)
    {
        var result = await _validation.ValidateAsync(news);
        if (!result.IsValid)
        {
            throw new ValidatinonException("Invalid data for news", 40002);
        }
        var newsReturned = await _repository.UpdateAsync(_mapper.Map<News>(news));
        if (newsReturned == null)
        {
            throw new NotFoundException($"News was not found", 40402);
        }
        return _mapper.Map<NewsResponseDto>(newsReturned);
    }

    public async Task DeleteNewsAsync(long id)
    {
        var res = await _repository.DeleteAsync(id);
        if (!res)
        {
            throw new NotFoundException($"News with {id} id was not found", 40403);
        }
    }
}