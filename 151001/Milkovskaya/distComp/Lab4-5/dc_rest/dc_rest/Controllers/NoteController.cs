using dc_rest.DTOs.RequestDTO;
using dc_rest.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace dc_rest.Controllers;


[ApiController]
[ApiVersion("1.0")]
[Route("api/v{v:apiVersion}/notes")]
public class NoteController : ControllerBase
{
    private INoteService _noteService;

    public NoteController(INoteService noteService)
    {
        _noteService = noteService;
    }
    
    [HttpGet]
    public async Task<IActionResult> GetNotes()
    {
        var notes = await _noteService.GetNotesAsync();
        return Ok(notes);
    }

    [HttpGet("{id}")]
    public async Task<IActionResult> GetNoteById(long id)
    {
        var note = await _noteService.GetNoteByIdAsync(id);
        return Ok(note);
    }

    [HttpPost]
    public async Task<IActionResult> CreateNote([FromBody] NoteRequestDto post)
    {
        var acceptLanguage = Request.Headers["Accept-Language"];
        var firstLanguage = acceptLanguage.ToString().Split(',').FirstOrDefault();
        post.Country = GetCountryCode();
        var createdNote = await _noteService.CreateNoteAsync(post);
        return CreatedAtAction(nameof(GetNoteById), new { id = createdNote.Id }, createdNote);
    }

    [HttpPut]
    public async Task<IActionResult> UpdateNote([FromBody] NoteRequestDto post)
    {
        var acceptLanguage = Request.Headers["Accept-Language"];
        var firstLanguage = acceptLanguage.ToString().Split(',').FirstOrDefault();
        post.Country = GetCountryCode();
        var updatedNote = await _noteService.UpdateNoteAsync(post);
        return Ok(updatedNote);
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteNote(long id)
    {
        await _noteService.DeleteNoteAsync(id);
        return NoContent();
    }
    
    private string GetCountryCode()
    {
        var languages = HttpContext.Request.Headers.AcceptLanguage.FirstOrDefault();

        if (languages is null)
        {
            return "us";
        }

        var country = languages
            .Split(",").First()
            .Split(";").First()
            .Split("-").Last();
        return country.ToLower();
    }
}