using Application.Contracts.RepositoryContracts;
using Application.Contracts.ServiceContracts;
using Application.Dto.Request;
using Application.Dto.Response;
using Application.Validation;
using AutoMapper;
using Domain.Entities;
using FluentValidation;

namespace Application.Services;

public class MarkService : IMarkService
{
    private readonly IMarkRepository _markRepository;
    private readonly IMapper _mapper;
    private readonly MarkRequestDtoValidator _validator;
    
    public MarkService(IMarkRepository markRepository, 
        IMapper mapper, MarkRequestDtoValidator validator)
    {
        _markRepository = markRepository;
        _mapper = mapper;
        _validator = validator;
    }
    
    public async Task<IEnumerable<MarkResponseDto>> GetMarksAsync()
    {
        var marks = await _markRepository.GetAllAsync();
        return _mapper.Map<IEnumerable<MarkResponseDto>>(marks);
    }

    public async Task<MarkResponseDto> GetMarkByIdAsync(long id)
    {
        var mark = await _markRepository.GetByIdAsync(id)
                  ?? throw new NotFoundException(ErrorCodes.MarkNotFound, ErrorMessages.MarkNotFoundMessage(id));
        return _mapper.Map<MarkResponseDto>(mark);
    }

    public async Task<MarkResponseDto> CreateMarkAsync(MarkRequestDto mark)
    {
        await _validator.ValidateAndThrowAsync(mark);
        var markToCreate = _mapper.Map<Mark>(mark);
        var createdMark = await _markRepository.CreateAsync(markToCreate);
        return _mapper.Map<MarkResponseDto>(createdMark);
    }

    public async Task<MarkResponseDto> UpdateMarkAsync(MarkRequestDto mark)
    {
        await _validator.ValidateAndThrowAsync(mark);
        var markToUpdate = _mapper.Map<Mark>(mark);
        var updatedMark = await _markRepository.UpdateAsync(markToUpdate)
                         ?? throw new NotFoundException(ErrorCodes.MarkNotFound, ErrorMessages.MarkNotFoundMessage(mark.Id));
        return _mapper.Map<MarkResponseDto>(updatedMark);
    }

    public async Task DeleteMarkAsync(long id)
    {
        if (!await _markRepository.DeleteAsync(id))
        {
            throw new NotFoundException(ErrorCodes.MarkNotFound, ErrorMessages.MarkNotFoundMessage(id));
        }
    }
}