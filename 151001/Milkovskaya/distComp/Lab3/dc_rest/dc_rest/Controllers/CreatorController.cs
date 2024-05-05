using dc_rest.DTOs.RequestDTO;
using dc_rest.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace dc_rest.Controllers;

[ApiController]
[ApiVersion("1.0")]
[Route("api/v{v:apiVersion}/creators")]
public class CreatorController : ControllerBase
{
    private ICreatorService _creatorService;
    
    public CreatorController(ICreatorService creatorService)
    {
        _creatorService = creatorService;
    }
    
    [HttpGet]
    public async Task<IActionResult> GetCreators()
    {
        var creators = await _creatorService.GetCreatorsAsync();
        return Ok(creators);
    }

    [HttpGet("{id}")]
    public async Task<IActionResult> GetCreatorById(long id)
    {
        var creator = await _creatorService.GetCreatorByIdAsync(id);
        return Ok(creator);
    }

    [HttpPost]
    public async Task<IActionResult> CreateCreator([FromBody] CreatorRequestDto post)
    {
        var createdCreator = await _creatorService.CreateCreatorAsync(post);
        return CreatedAtAction(nameof(GetCreatorById), new { id = createdCreator.Id }, createdCreator);
    }

    [HttpPut]
    public async Task<IActionResult> UpdateCreator([FromBody] CreatorRequestDto post)
    {
        var updatedCreator = await _creatorService.UpdateCreatorAsync(post);
        return Ok(updatedCreator);
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteCreator(long id)
    {
        await _creatorService.DeleteCreatorAsync(id);
        return NoContent();
    }
}