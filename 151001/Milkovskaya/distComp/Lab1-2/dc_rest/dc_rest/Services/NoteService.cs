using AutoMapper;
using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;
using dc_rest.Exceptions;
using dc_rest.Models;
using dc_rest.Repositories.Interfaces;
using dc_rest.Services.Interfaces;
using FluentValidation;

namespace dc_rest.Services;

public class NoteService : INoteService
{
    private IMapper _mapper;
    private INoteRepository _repository;
    private AbstractValidator<NoteRequestDto> _validation;
    public NoteService(IMapper mapper, INoteRepository repository, AbstractValidator<NoteRequestDto> validator)
    {
        _mapper = mapper;
        _repository = repository;
        _validation = validator;
    }
    public async Task<IEnumerable<NoteResponseDto>> GetNotesAsync()
    {   
        var notes = await _repository.GetAllAsync();
        return _mapper.Map<IEnumerable<NoteResponseDto>>(notes);
    }

    public async Task<NoteResponseDto> GetNoteByIdAsync(long id)
    {
        var note = await _repository.GetByIdAsync(id);
        if (note == null)
        {
            throw new NotFoundException($"Note with {id} id was not found", 400401);
        }
        return _mapper.Map<NoteResponseDto>(note);
    }

    public async Task<NoteResponseDto> CreateNoteAsync(NoteRequestDto note)
    {
        var result = await _validation.ValidateAsync(note);
        if (!result.IsValid)
        {
            throw new ValidatinonException("Invalid data for note", 40001);
        }
        var noteReturned = await _repository.CreateAsync(_mapper.Map<Note>(note));
        return _mapper.Map<NoteResponseDto>(noteReturned);
    }

    public async Task<NoteResponseDto> UpdateNoteAsync(NoteRequestDto note)
    {
        var result = await _validation.ValidateAsync(note);
        if (!result.IsValid)
        {
            throw new ValidatinonException("Invalid data for note", 40002);
        }
        var noteReturned = await _repository.UpdateAsync(_mapper.Map<Note>(note));
        if (noteReturned == null)
        {
            throw new NotFoundException($"Note was not found", 400402);
        }
        return _mapper.Map<NoteResponseDto>(noteReturned);
    }

    public async Task DeleteNoteAsync(long id)
    {
        var res = await _repository.DeleteAsync(id);
        if (!res)
        {
            throw new NotFoundException($"Note with {id} id was not found", 400403);
        }
    }
}