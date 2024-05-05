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

public class LabelService : ILabelService
{
    private IMapper _mapper;
    private ILabelRepository _repository;
    private AbstractValidator<LabelRequestDto> _validation;
    private ICacheService _cacheService;
    private const string Prefix = "news-";
    public LabelService(IMapper mapper, ILabelRepository repository, AbstractValidator<LabelRequestDto> validator, ICacheService cacheService)
    {
        _mapper = mapper;
        _repository = repository;
        _validation = validator;
        _cacheService = cacheService;
    }
    public async Task<IEnumerable<LabelResponseDto>> GetLabelsAsync()
    {
        var editors = (await _repository.GetAllAsync()).ToList();
        var result = new List<LabelResponseDto>(editors.Count);

        foreach (var editor in editors)
        {
            await _cacheService.SetAsync(Prefix + editor.Id, editor);
            result.Add(_mapper.Map<LabelResponseDto>(editor));
        }
        return result;
    }

    public async Task<LabelResponseDto> GetLabelByIdAsync(long id)
    {
        var creator = await _cacheService.GetAsync(Prefix + id,
            async () => await _repository.GetByIdAsync(id));
        //var creator = await _repository.GetByIdAsync(id);
        if (creator == null)
        {
            throw new NotFoundException($"Label with {id} id was not found", 400401);
        }
        return _mapper.Map<LabelResponseDto>(creator);
    }

    public async Task<LabelResponseDto> CreateLabelAsync(LabelRequestDto label)
    {
        var result = await _validation.ValidateAsync(label);
        if (!result.IsValid)
        {
            throw new ValidatinonException("Invalid data for label", 40001);
        }
        var creatorReturned = await _repository.CreateAsync(_mapper.Map<Label>(label));
        await _cacheService.SetAsync(Prefix + creatorReturned.Id, creatorReturned);
        return _mapper.Map<LabelResponseDto>(creatorReturned);
    }

    public async Task<LabelResponseDto> UpdateLabelAsync(LabelRequestDto label)
    {
        var result = await _validation.ValidateAsync(label);
        if (!result.IsValid)
        {
            throw new ValidatinonException("Invalid data for label", 40002);
        }
        var creatorReturned = await _repository.UpdateAsync(_mapper.Map<Label>(label));
        if (creatorReturned == null)
        {
            throw new NotFoundException($"Label was not found", 40402);
        }
        await _cacheService.SetAsync(Prefix + creatorReturned.Id, creatorReturned);
        return _mapper.Map<LabelResponseDto>(creatorReturned);
    }

    public async Task DeleteLabelAsync(long id)
    {
        var res = await _repository.DeleteAsync(id);
        if (!res)
        {
            throw new NotFoundException($"Label with {id} id was not found", 40403);
        }
        await _cacheService.RemoveAsync(Prefix + id);
    }
}