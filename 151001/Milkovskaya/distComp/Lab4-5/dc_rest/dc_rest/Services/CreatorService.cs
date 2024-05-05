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

public class CreatorService(
    IMapper mapper,
    ICreatorRepository repository,
    AbstractValidator<CreatorRequestDto> validator, ICacheService cacheService)
    : ICreatorService
{
    private IMapper _mapper = mapper;
    private ICreatorRepository _repository = repository;
    private AbstractValidator<CreatorRequestDto> _validation = validator;
    private ICacheService _cacheService = cacheService;
    private const string Prefix = "creator-";

    public async Task<IEnumerable<CreatorResponseDto>> GetCreatorsAsync()
    {
        var editors = (await _repository.GetAllAsync()).ToList();
        var result = new List<CreatorResponseDto>(editors.Count);

        foreach (var editor in editors)
        {
            await cacheService.SetAsync(Prefix + editor.Id, editor);
            result.Add(mapper.Map<CreatorResponseDto>(editor));
        }
        return result;
        var creators = await _repository.GetAllAsync();
        return _mapper.Map<IEnumerable<CreatorResponseDto>>(creators);
    }

    public async Task<CreatorResponseDto> GetCreatorByIdAsync(long id)
    {
        var creator = await cacheService.GetAsync(Prefix + id,
            async () => await _repository.GetByIdAsync(id));
        //var creator = await _repository.GetByIdAsync(id);
        if (creator == null)
        {
            throw new NotFoundException($"Creator with {id} id was not found", 400401);
        }
        return _mapper.Map<CreatorResponseDto>(creator);
    }

    public async Task<CreatorResponseDto> CreateCreatorAsync(CreatorRequestDto creator)
    {
        var result = await _validation.ValidateAsync(creator);
        if (!result.IsValid)
        {
            throw new ValidatinonException("Invalid data for creator", 40001);
        }
        var creatorReturned = await _repository.CreateAsync(_mapper.Map<Creator>(creator));
        await cacheService.SetAsync(Prefix + creatorReturned.Id, creatorReturned);
        return _mapper.Map<CreatorResponseDto>(creatorReturned);
    }

    public async Task<CreatorResponseDto> UpdateCreatorAsync(CreatorRequestDto creator)
    {
        var result = await _validation.ValidateAsync(creator);
        if (!result.IsValid)
        {
            throw new ValidatinonException("Invalid data for creator", 40002);
        }
        var creatorReturned = await _repository.UpdateAsync(_mapper.Map<Creator>(creator));
        if (creatorReturned == null)
        {
            throw new NotFoundException($"Creator was not found", 40402);
        }
        await cacheService.SetAsync(Prefix + creatorReturned.Id, creatorReturned);
        return _mapper.Map<CreatorResponseDto>(creatorReturned);
    }

    public async Task DeleteCreatorAsync(long id)
    {
        var res = await _repository.DeleteAsync(id);
        if (!res)
        {
            throw new NotFoundException($"Creator with {id} id was not found", 40403);
        }
        await cacheService.RemoveAsync(Prefix + id);
    }
}