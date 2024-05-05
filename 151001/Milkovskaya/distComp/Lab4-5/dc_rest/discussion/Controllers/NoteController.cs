
using System.ComponentModel.DataAnnotations;
using discussion.Models;
using discussion.Services;
using discussion.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Primitives;

namespace discussion.Controllers;

[ApiController]
[ApiVersion("1.0")]
[Route("api/v{v:apiVersion}/notes")]
public class NoteController : ControllerBase
{
    private readonly INoteService _noteService;

    public NoteController(INoteService noteService)
    {
        _noteService = noteService;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<Note>>> GetAllNotes()
    {
        var notes = await _noteService.GetAllNotesAsync();
        return Ok(notes);
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<Note>> GetNoteById(long id)
    {
        var note = await _noteService.GetNoteByIdAsync(new NoteKey(){Id = id});
        return Ok(note);
    }

    [HttpPost]
    public async Task<IActionResult> CreateNote([FromBody] NoteRequestDto post)
    {
        var acceptLanguage = Request.Headers["Accept-Language"];
        var firstLanguage = acceptLanguage.ToString().Split(',').FirstOrDefault();
        if (firstLanguage != "")
        {
            post.Country = firstLanguage;
        }
        post.Id = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds();
        var note = await _noteService.AddNoteAsync(post);
        return Ok(note);
    }

    [HttpPut]
    public async Task<IActionResult> UpdateNote([FromBody] NoteRequestDto post)
    {
        var acceptLanguage = Request.Headers["Accept-Language"];
        var firstLanguage = acceptLanguage.ToString().Split(',').FirstOrDefault();
        if (firstLanguage != "")
        {
            post.Country = firstLanguage;
        }
        var note  = await _noteService.UpdateNoteAsync(post);
        return Ok(note);
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteNote(long id)
    {
        await _noteService.DeleteNoteAsync(new NoteKey(){Id = id});
        return NoContent();
    }
}