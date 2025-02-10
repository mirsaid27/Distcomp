using Service.DTO.Request.Editor;
using Service.DTO.Request.Article;
using Service.DTO.Request.Post;
using Service.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace Presentation.Controllers;

[ApiController]
[Route("/api/v1.0/articles")]
public class ArticleController(IArticleService articleService, IEditorService _editorService, IPostService _postService)
    : ControllerBase
{
    [HttpGet]
    public async Task<IActionResult> GetAllArticles()
    {
        var article = await articleService.GetArticle(new ArticleRequestToGetAll());
        return Ok(article);
    }

    [HttpGet("{id:long}/editor")]
    public async Task<IActionResult> GetEditorByArticleId(long id)
    {
        var editor = await _editorService.GetEditorByArticleId(new EditorRequestToGetByArticleId { ArticleId = id });
        return Ok(editor);
    }

    [HttpGet("{id:long}/posts")]
    public async Task<IActionResult> GetPostsByArticleId(long id)
    {
        var posts = await _postService.GetPostsByArticleId(new PostRequestToGetByArticleId { ArticleId = id });
        return Ok(posts);
    }

    [HttpGet("{id:long}")]
    public async Task<IActionResult> GetArticleById(long id)
    {
        var article = await articleService.GetArticleById(new ArticleRequestToGetById { Id = id });
        return Ok(article);
    }

    [HttpPost]
    public async Task<IActionResult> CreateArticle(ArticleRequestToCreate articleRequestToCreate)
    {
        var article = await articleService.CreateArticle(articleRequestToCreate);
        return CreatedAtAction(nameof(GetArticleById), new { id = article.Id }, article);
    }

    [HttpPut]
    public async Task<IActionResult> UpdateArticle(ArticleRequestToFullUpdate articleModel)
    {
        var article = await articleService.UpdateArticle(articleModel);
        return Ok(article);
    }

    [HttpDelete("{id:long}")]
    public async Task<IActionResult> DeleteArticle(long id)
    {
        await articleService.DeleteArticle(new ArticleRequestToDeleteById { Id = id });
        return NoContent();
    }
}