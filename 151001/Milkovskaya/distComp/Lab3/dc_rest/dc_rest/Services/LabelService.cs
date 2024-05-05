using AutoMapper;
using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;
using dc_rest.Exceptions;
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
    public LabelService(IMapper mapper, ILabelRepository repository, AbstractValidator<LabelRequestDto> validator)
    {
        _mapper = mapper;
        _repository = repository;
        _validation = validator;
    }
    public async Task<IEnumerable<LabelResponseDto>> GetLabelsAsync()
    {
        var labels = await _repository.GetAllAsync();
        return _mapper.Map<IEnumerable<LabelResponseDto>>(labels);
    }

    public async Task<LabelResponseDto> GetLabelByIdAsync(long id)
    {
        var label = await _repository.GetByIdAsync(id);
        if (label == null)
        {
            throw new NotFoundException($"Label with {id} id was not found", 400401);
        }
        return _mapper.Map<LabelResponseDto>(label);
    }

    public async Task<LabelResponseDto> CreateLabelAsync(LabelRequestDto label)
    {
        var result = await _validation.ValidateAsync(label);
        if (!result.IsValid)
        {
            throw new ValidatinonException("Invalid data for label", 40001);
        }
        var labelReturned = await _repository.CreateAsync(_mapper.Map<Label>(label));
        return _mapper.Map<LabelResponseDto>(labelReturned);
    }

    public async Task<LabelResponseDto> UpdateLabelAsync(LabelRequestDto label)
    {
        var result = await _validation.ValidateAsync(label);
        if (!result.IsValid)
        {
            throw new ValidatinonException("Invalid data for label", 40002);
        }
        var labelReturned = await _repository.UpdateAsync(_mapper.Map<Label>(label));
        if (labelReturned == null)
        {
            throw new NotFoundException($"Label was not found", 40402);
        }
        return _mapper.Map<LabelResponseDto>(labelReturned);
    }

    public async Task DeleteLabelAsync(long id)
    {
        var res = await _repository.DeleteAsync(id);
        if (!res)
        {
            throw new NotFoundException($"Label with {id} id was not found", 40403);
        }
    }
}