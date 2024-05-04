using AutoMapper;
using FluentValidation;
using Lab1.DTO.RequestDTO;
using Lab1.DTO.ResponseDTO;
using Lab1.Exceptions;
using Lab1.Infrastructure.Validators;
using Lab1.Models;
using Lab1.Repositories.Interfaces;
using Lab1.Services.Interfaces;

namespace Lab1.Services.Impementations
{
    public class NoteService(INoteRepository noteRepository,
        IMapper mapper, NoteRequestDtoValidator validator) : INoteService
    {
        private readonly INoteRepository _noteRepository = noteRepository;
        private readonly IMapper _mapper = mapper;
        private readonly NoteRequestDtoValidator _validator = validator;

        public async Task<IEnumerable<NoteResponseDto>> GetNotesAsync()
        {
            var posts = await _noteRepository.GetAllAsync();
            return _mapper.Map<IEnumerable<NoteResponseDto>>(posts);
        }

        public async Task<NoteResponseDto> GetNoteByIdAsync(long id)
        {
            var post = await _noteRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.NoteNotFoundMessage(id));
            return _mapper.Map<NoteResponseDto>(post);
        }

        public async Task<NoteResponseDto> CreateNoteAsync(NoteRequestDto post)
        {
            _validator.ValidateAndThrow(post);
            var postToCreate = _mapper.Map<Note>(post);
            var createdPost = await _noteRepository.CreateAsync(postToCreate);
            return _mapper.Map<NoteResponseDto>(createdPost);
        }

        public async Task<NoteResponseDto> UpdateNoteAsync(NoteRequestDto post)
        {
            _validator.ValidateAndThrow(post);
            var postToUpdate = _mapper.Map<Note>(post);
            var updatedPost = await _noteRepository.UpdateAsync(postToUpdate)
                ?? throw new NotFoundException(ErrorMessages.NoteNotFoundMessage(post.Id));
            return _mapper.Map<NoteResponseDto>(updatedPost);
        }

        public async Task DeleteNoteAsync(long id)
        {
            if (!await _noteRepository.DeleteAsync(id))
            {
                throw new NotFoundException(ErrorMessages.NoteNotFoundMessage(id));
            }
        }

    }
}
