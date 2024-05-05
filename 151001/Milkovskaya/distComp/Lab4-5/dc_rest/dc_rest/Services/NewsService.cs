using AutoMapper;
using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;
using dc_rest.Exceptions;
using dc_rest.Infrastructure.Interfaces;
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
    private ICacheService _cacheService;
    private const string Prefix = "news-";
    public NewsService(IMapper mapper, INewsRepository repository, AbstractValidator<NewsRequestDto> validator, ICacheService cacheService)
    {
        _mapper = mapper;
        _repository = repository;
        _validation = validator;
        _cacheService = cacheService;
    }
    public async Task<IEnumerable<NewsResponseDto>> GetNewsAsync()
    {
        var editors = (await _repository.GetAllAsync()).ToList();
        var result = new List<NewsResponseDto>(editors.Count);

        foreach (var editor in editors)
        {
            await _cacheService.SetAsync(Prefix + editor.Id, editor);
            result.Add(_mapper.Map<NewsResponseDto>(editor));
        }
        return result;
    }

    public async Task<NewsResponseDto> GetNewsByIdAsync(long id)
    {
        var creator = await _cacheService.GetAsync(Prefix + id,
            async () => await _repository.GetByIdAsync(id));
        //var creator = await _repository.GetByIdAsync(id);
        if (creator == null)
        {
            throw new NotFoundException($"News with {id} id was not found", 400401);
        }
        return _mapper.Map<NewsResponseDto>(creator);
    }

    public async Task<NewsResponseDto> CreateNewsAsync(NewsRequestDto news)
    {
        var result = await _validation.ValidateAsync(news);
        if (!result.IsValid)
        {
            throw new ValidatinonException("Invalid data for news", 40001);
        }
        var creatorReturned = await _repository.CreateAsync(_mapper.Map<News>(news));
        await _cacheService.SetAsync(Prefix + creatorReturned.Id, creatorReturned);
        return _mapper.Map<NewsResponseDto>(creatorReturned);
    }

    public async Task<NewsResponseDto> UpdateNewsAsync(NewsRequestDto news)
    {
        var result = await _validation.ValidateAsync(news);
        if (!result.IsValid)
        {
            throw new ValidatinonException("Invalid data for news", 40002);
        }
        var creatorReturned = await _repository.UpdateAsync(_mapper.Map<News>(news));
        if (creatorReturned == null)
        {
            throw new NotFoundException($"News was not found", 40402);
        }
        await _cacheService.SetAsync(Prefix + creatorReturned.Id, creatorReturned);
        return _mapper.Map<NewsResponseDto>(creatorReturned);
    }

    public async Task DeleteNewsAsync(long id)
    {
        var res = await _repository.DeleteAsync(id);
        if (!res)
        {
            throw new NotFoundException($"News with {id} id was not found", 40403);
        }
        await _cacheService.RemoveAsync(Prefix + id);
    }
}