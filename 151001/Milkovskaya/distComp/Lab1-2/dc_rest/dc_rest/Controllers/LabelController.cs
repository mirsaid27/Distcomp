using dc_rest.DTOs.RequestDTO;
using dc_rest.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace dc_rest.Controllers;


[ApiController]
[ApiVersion("1.0")]
[Route("api/v{v:apiVersion}/labels")]
public class LabelController : ControllerBase
{
    private ILabelService _labelService;

    public LabelController(ILabelService labelService)
    {
        _labelService = labelService;
    }
    
    [HttpGet]
    public async Task<IActionResult> GetLabels()
    {
        var labels = await _labelService.GetLabelsAsync();
        return Ok(labels);
    }

    [HttpGet("{id}")]
    public async Task<IActionResult> GetLabelById(long id)
    {
        var label = await _labelService.GetLabelByIdAsync(id);
        return Ok(label);
    }

    [HttpPost]
    public async Task<IActionResult> CreateLabel([FromBody] LabelRequestDto post)
    {
        var createdLabel = await _labelService.CreateLabelAsync(post);
        return CreatedAtAction(nameof(GetLabelById), new { id = createdLabel.Id }, createdLabel);
    }

    [HttpPut]
    public async Task<IActionResult> UpdateLabel([FromBody] LabelRequestDto post)
    {
        var updatedLabel = await _labelService.UpdateLabelAsync(post);
        return Ok(updatedLabel);
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteLabel(long id)
    {
        await _labelService.DeleteLabelAsync(id);
        return NoContent();
    }
}