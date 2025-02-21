using Application.Contracts.ServiceContracts;
using Application.Dto.Request;
using Microsoft.AspNetCore.Mvc;

namespace Presentation.Controllers;

[ApiController]
[Route("api/v1.0/[controller]")]
public class ArticlesController : ControllerBase
{
    private readonly IArticleService _articleService;

    public ArticlesController(IArticleService articleService)
    {
        _articleService = articleService;
    }

    [HttpGet]
    public async Task<IActionResult> GetArticles()
    {
        var articles = await _articleService.GetArticlesAsync();
        return Ok(articles);
    }

    [HttpGet("{id}")]
    public async Task<IActionResult> GetArticleById(long id)
    {
        var article = await _articleService.GetArticleByIdAsync(id);
        return Ok(article);
    }

    [HttpPost]
    public async Task<IActionResult> CreateArticle([FromBody] ArticleRequestDto article)
    {
        var createdArticle = await _articleService.CreateArticleAsync(article);
        return CreatedAtAction(nameof(CreateArticle), new { id = createdArticle.Id }, createdArticle);
    }
    
    [HttpPut]
    public async Task<IActionResult> UpdateArticle([FromBody] ArticleRequestDto article)
    {
        var updatedArticle = await _articleService.UpdateArticleAsync(article);
        return Ok(updatedArticle);
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteArticle(long id)
    {
        await _articleService.DeleteArticleAsync(id);
        return NoContent();
    }
}