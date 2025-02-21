using Application.Dto.Request;
using Application.Dto.Response;

namespace Application.Contracts.ServiceContracts;

public interface IArticleService
{
    Task<IEnumerable<ArticleResponseDto>> GetArticlesAsync();

    Task<ArticleResponseDto> GetArticleByIdAsync(long id);

    Task<ArticleResponseDto> CreateArticleAsync(ArticleRequestDto article);

    Task<ArticleResponseDto> UpdateArticleAsync(ArticleRequestDto article);

    Task DeleteArticleAsync(long id);
}