using Application.Contracts.RepositoryContracts;
using Application.Contracts.ServiceContracts;
using Application.Dto.Request;
using Application.Dto.Response;
using Application.Validation;
using AutoMapper;
using Domain.Entities;
using FluentValidation;

namespace Application.Services;

public class EditorService : IEditorService
{
    private readonly IEditorRepository _editorRepository;
    private readonly IMapper _mapper;
    private readonly EditorRequestDtoValidator _validator;
    
    public EditorService(IEditorRepository editorRepository, 
        IMapper mapper, EditorRequestDtoValidator validator)
    {
        _editorRepository = editorRepository;
        _mapper = mapper;
        _validator = validator;
    }
    
    public async Task<IEnumerable<EditorResponseDto>> GetEditorsAsync()
    {
        var editors = await _editorRepository.GetAllAsync();
        return _mapper.Map<IEnumerable<EditorResponseDto>>(editors);
    }

    public async Task<EditorResponseDto> GetEditorByIdAsync(long id)
    {
        var editor = await _editorRepository.GetByIdAsync(id)
                   ?? throw new NotFoundException(ErrorCodes.EditorNotFound, ErrorMessages.EditorNotFoundMessage(id));
        return _mapper.Map<EditorResponseDto>(editor);
    }

    public async Task<EditorResponseDto> CreateEditorAsync(EditorRequestDto editor)
    {
        await _validator.ValidateAndThrowAsync(editor);
        var editorToCreate = _mapper.Map<Editor>(editor);
        var createdEditor = await _editorRepository.CreateAsync(editorToCreate);
        return _mapper.Map<EditorResponseDto>(createdEditor);
    }

    public async Task<EditorResponseDto> UpdateEditorAsync(EditorRequestDto editor)
    {
        await _validator.ValidateAndThrowAsync(editor);
        var editorToUpdate = _mapper.Map<Editor>(editor);
        var updatedEditor = await _editorRepository.UpdateAsync(editorToUpdate)
                          ?? throw new NotFoundException(ErrorCodes.EditorNotFound, ErrorMessages.EditorNotFoundMessage(editor.Id));
        return _mapper.Map<EditorResponseDto>(updatedEditor);
    }

    public async Task DeleteEditorAsync(long id)
    {
        if (!await _editorRepository.DeleteAsync(id))
        {
            throw new NotFoundException(ErrorCodes.EditorNotFound, ErrorMessages.EditorNotFoundMessage(id));
        }
    }
}