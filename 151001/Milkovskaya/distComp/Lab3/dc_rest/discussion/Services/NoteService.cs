
using AutoMapper;
using dc_rest.Exceptions;
using discussion.Models;
using discussion.Repositories.Interfaces;
using discussion.Services.Interfaces;
using FluentValidation;

namespace discussion.Services;

public class NoteService : INoteService
{
    private readonly INoteRepository _noteRepository;
    private readonly IMapper _autoMapper;
    private readonly AbstractValidator<NoteRequestDto> _validator;

    public NoteService(INoteRepository noteRepository, IMapper autoMapper, AbstractValidator<NoteRequestDto> validator)
    {
        _noteRepository = noteRepository;
        _autoMapper = autoMapper;
        _validator = validator;
    }

    public async Task<IEnumerable<NoteResponseDto>> GetAllNotesAsync()
    {
        var notes =  await _noteRepository.GetAllAsync();
        return _autoMapper.Map<IEnumerable<NoteResponseDto>>(notes);
    }

    public async Task<NoteResponseDto> GetNoteByIdAsync(long id)
    {
        var note =  await _noteRepository.GetByIdAsync(id);
        return _autoMapper.Map<NoteResponseDto>(note);
    }

    public async Task<NoteResponseDto> AddNoteAsync(NoteRequestDto note)
    {
        note.Id = DateTime.Now.Nanosecond;
        var validationResult = await _validator.ValidateAsync(note);
        if (!validationResult.IsValid)
        {
            throw new ValidatinonException("Invalid data for note", 40301);
        }
        var res = await _noteRepository.CreateAsync(_autoMapper.Map<Note>(note));
        return _autoMapper.Map<NoteResponseDto>(res);
    }

    public async Task<NoteResponseDto> UpdateNoteAsync(NoteRequestDto note)
    {
        var validationResult = await _validator.ValidateAsync(note);
        if (!validationResult.IsValid)
        {
            throw new ValidatinonException("Invalid data for note", 40301);
        }
        var res = await _noteRepository.UpdateAsync(_autoMapper.Map<Note>(note));
        return _autoMapper.Map<NoteResponseDto>(res);
    }

    public async Task DeleteNoteAsync(long id)
    {
        await _noteRepository.DeleteAsync(id);
    }
}