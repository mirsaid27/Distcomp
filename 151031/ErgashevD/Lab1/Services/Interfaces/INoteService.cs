using Lab1.DTO.RequestDTO;
using Lab1.DTO.ResponseDTO;

namespace Lab1.Services.Interfaces
{
    public interface INoteService
    {
        Task<IEnumerable<NoteResponseDto>> GetNotesAsync();

        Task<NoteResponseDto> GetNoteByIdAsync(long id);

        Task<NoteResponseDto> CreateNoteAsync(NoteRequestDto post);

        Task<NoteResponseDto> UpdateNoteAsync(NoteRequestDto post);

        Task DeleteNoteAsync(long id);
    }
}
