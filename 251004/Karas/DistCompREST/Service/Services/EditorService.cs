using Service.DTO.Request;
using Service.DTO.Response;
using AutoMapper;
using Domain.Entities;
using Domain.Exceptions;
using Domain.Repository;
using Service.DTO.Request.Editor;
using Service.DTO.Response.Editor;
using Service.Interfaces;

namespace Service.Services;

public class EditorService : IEditorService
{
    private readonly IEditorRepository _editorRepository;
    private readonly IArticleRepository _articleRepository;
    private readonly IMapper _mapper;

    public EditorService(IEditorRepository editorRepository,IArticleRepository articleRepository, IMapper mapper)
    {
        _editorRepository = editorRepository;
        _articleRepository = articleRepository;
        _mapper = mapper;
    }
    
    public async Task<EditorResponseToGetById?> CreateEditor(EditorRequestToCreate model)
    {
        var editor = _mapper.Map<Editor>(model);
        Validate(editor);
        editor = await _editorRepository.AddEditor(editor);
        return _mapper.Map<EditorResponseToGetById?>(editor);
    }

    public async Task<IEnumerable<EditorResponseToGetById?>?> GetEditors(EditorRequestToGetAll request)
    {
        var editors = await _editorRepository.GetAllEditors();
        return editors.Select(_mapper.Map<EditorResponseToGetById?>);
    }

    public async Task<EditorResponseToGetById?> GetEditorById(EditorRequestToGetById request)
    {
        var editor = await _editorRepository.GetEditor(request.Id);
        return _mapper.Map<EditorResponseToGetById>(editor);
    }
    public async Task<EditorResponseToGetById?> GetEditorByArticleId(EditorRequestToGetByArticleId request)
    {
        var article = await _articleRepository.GetArticle(request.ArticleId);
        var editor = await _editorRepository.GetEditor(article.EditorId);
        return _mapper.Map<EditorResponseToGetById>(editor);
    }

    public async Task<EditorResponseToGetById?> UpdateEditor(EditorRequestToFullUpdate model)
    {
        var editor = _mapper.Map<Editor>(model);
        Validate(editor);
        var editorProj = await _editorRepository.UpdateEditor(editor);
        return _mapper.Map<EditorResponseToGetById>(editorProj);
    }

    public async Task<EditorResponseToGetById?> DeleteEditor(EditorRequestToDeleteById request)
    {
        var editor = await _editorRepository.RemoveEditor(request.Id);
        return _mapper.Map<EditorResponseToGetById>(editor);
    }
    
    private bool Validate(Editor editor)
    {
        var errors = new Dictionary<string, string[]>();
        if (editor.Firstname.Length < 2 || editor.Firstname.Length > 64)
        {
            errors.Add("Firstname",["Should be from 2 to 64 chars"]);
        }
        if (editor.Lastname.Length < 2 || editor.Lastname.Length > 64)
        {
            errors.Add("Lastname",["Should be from 2 to 64 chars"]);
        }
        if (editor.Password.Length < 8 || editor.Firstname.Length > 128)
        {
            errors.Add("Password",["Should be from 8 to 128 chars"]);
        }
        if (editor.Login.Length < 2 || editor.Login.Length > 64)
        {
            errors.Add("Login",["Should be from 2 to 64 chars"]);
        }

        if (errors.Count != 0)
        {
            throw new BadRequestException("Validation error", errors);
        }
        return true;
    }
}