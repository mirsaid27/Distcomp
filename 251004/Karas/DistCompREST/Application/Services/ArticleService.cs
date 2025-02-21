using Application.Contracts.RepositoryContracts;
using Application.Contracts.ServiceContracts;
using Application.Dto.Request;
using Application.Dto.Response;
using Application.Validation;
using AutoMapper;
using Domain.Entities;
using FluentValidation;

namespace Application.Services;

public class ArticleService : IArticleService
{
    private readonly IArticleRepository _articleRepository;
    private readonly IEditorRepository _editorRepository;
    private readonly IMapper _mapper;
    private readonly ArticleRequestDtoValidator _validator;

    public ArticleService(IArticleRepository articleRepository, IEditorRepository editorRepository,
        IMapper mapper, ArticleRequestDtoValidator validator)
    {
        _articleRepository = articleRepository;
        _editorRepository = editorRepository;
        _mapper = mapper;
        _validator = validator;
    }
    
    public async Task<IEnumerable<ArticleResponseDto>> GetArticlesAsync()
    {
        var stories = await _articleRepository.GetAllAsync();
        return _mapper.Map<IEnumerable<ArticleResponseDto>>(stories);
    }

    public async Task<ArticleResponseDto> GetArticleByIdAsync(long id)
    {
        var article = await _articleRepository.GetByIdAsync(id)
                    ?? throw new NotFoundException(ErrorCodes.ArticleNotFound, ErrorMessages.ArticleNotFoundMessage(id));
        return _mapper.Map<ArticleResponseDto>(article);
    }

    public async Task<ArticleResponseDto> CreateArticleAsync(ArticleRequestDto article)
    {
        await _validator.ValidateAndThrowAsync(article);
        var articleToCreate = _mapper.Map<Article>(article);

        articleToCreate.EditorId = article.EditorId;
        articleToCreate.Created = DateTime.UtcNow;
        articleToCreate.Modified = DateTime.UtcNow;
        
        var createdArticle = await _articleRepository.CreateAsync(articleToCreate);
        return _mapper.Map<ArticleResponseDto>(createdArticle);
    }

    public async Task<ArticleResponseDto> UpdateArticleAsync(ArticleRequestDto article)
    {
        await _validator.ValidateAndThrowAsync(article);
        var articleToUpdate = _mapper.Map<Article>(article);
        
        articleToUpdate.Modified = DateTime.UtcNow;
        
        var updatedArticle = await _articleRepository.UpdateAsync(articleToUpdate)
                           ?? throw new NotFoundException(ErrorCodes.ArticleNotFound, ErrorMessages.ArticleNotFoundMessage(article.Id));
        return _mapper.Map<ArticleResponseDto>(updatedArticle);
    }

    public async Task DeleteArticleAsync(long id)
    {
        if (!await _articleRepository.DeleteAsync(id))
        {
            throw new NotFoundException(ErrorCodes.ArticleNotFound, ErrorMessages.ArticleNotFoundMessage(id));
        }
    }
}