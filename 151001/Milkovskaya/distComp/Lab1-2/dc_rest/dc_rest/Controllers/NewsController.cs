using dc_rest.DTOs.RequestDTO;
using dc_rest.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace dc_rest.Controllers;

[ApiController]
[ApiVersion("1.0")]
[Route("api/v{v:apiVersion}/news")]
public class NewsController : ControllerBase
{
    private INewsService _newsService;

    public NewsController(INewsService newsService)
    {
        _newsService = newsService;
    }
    
    [HttpGet]
    public async Task<IActionResult> GetNewss()
    {
        var newss = await _newsService.GetNewsAsync();
        return Ok(newss);
    }

    [HttpGet("{id}")]
    public async Task<IActionResult> GetNewsById(long id)
    {
        var news = await _newsService.GetNewsByIdAsync(id);
        return Ok(news);
    }

    [HttpPost]
    public async Task<IActionResult> CreateNews([FromBody] NewsRequestDto post)
    {
        var createdNews = await _newsService.CreateNewsAsync(post);
        return CreatedAtAction(nameof(GetNewsById), new { id = createdNews.Id }, createdNews);
    }

    [HttpPut]
    public async Task<IActionResult> UpdateNews([FromBody] NewsRequestDto post)
    {
        var updatedNews = await _newsService.UpdateNewsAsync(post);
        return Ok(updatedNews);
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteNews(long id)
    {
        await _newsService.DeleteNewsAsync(id);
        return NoContent();
    }
}