using Application.Contracts.ServiceContracts;
using Application.Dto.Request;
using Microsoft.AspNetCore.Mvc;

namespace Presentation.Controllers;

[ApiController]
[Route("api/v1.0/[controller]")]
public class EditorsController : ControllerBase
{
    private readonly IEditorService _editorService;

    public EditorsController(IEditorService editorService)
    {
        _editorService = editorService;
    }

    [HttpGet]
    public async Task<IActionResult> GetEditors()
    {
        var editors = await _editorService.GetEditorsAsync();
        return Ok(editors);
    }

    [HttpGet("{id}")]
    public async Task<IActionResult> GetEditorById(long id)
    {
        var editor = await _editorService.GetEditorByIdAsync(id);
        return Ok(editor);
    }

    [HttpPost]
    public async Task<IActionResult> CreateEditor([FromBody] EditorRequestDto editor)
    {
        var createdEditor = await _editorService.CreateEditorAsync(editor);
        return CreatedAtAction(nameof(CreateEditor), new { id = createdEditor.Id }, createdEditor);
    }
    
    [HttpPut]
    public async Task<IActionResult> UpdateEditor([FromBody] EditorRequestDto editor)
    {
        var updatedEditor = await _editorService.UpdateEditorAsync(editor);
        return Ok(updatedEditor);
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteEditor(long id)
    {
        await _editorService.DeleteEditorAsync(id);
        return NoContent();
    }
}