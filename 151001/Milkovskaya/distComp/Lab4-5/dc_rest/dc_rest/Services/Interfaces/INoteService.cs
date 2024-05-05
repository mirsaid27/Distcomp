using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;

namespace dc_rest.Services.Interfaces;

public interface INoteService
{
    Task<IEnumerable<NoteResponseDto>> GetNotesAsync();

    Task<NoteResponseDto> GetNoteByIdAsync(long id);

    Task<NoteResponseDto> CreateNoteAsync(NoteRequestDto post);

    Task<NoteResponseDto> UpdateNoteAsync(NoteRequestDto post);

    Task DeleteNoteAsync(long id);
}