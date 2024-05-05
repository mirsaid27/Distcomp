
using discussion.Models;

namespace discussion.Services.Interfaces;

public interface INoteService
{
    Task<IEnumerable<NoteResponseDto>> GetAllNotesAsync();

    Task<NoteResponseDto> GetNoteByIdAsync(NoteKey id);

    Task<NoteResponseDto> AddNoteAsync(NoteRequestDto note);

    Task<NoteResponseDto> UpdateNoteAsync(NoteRequestDto note);

    Task DeleteNoteAsync(NoteKey id);
}