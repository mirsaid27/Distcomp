using Task310.DTO.RequestDTO;
using Task310.DTO.ResponseDTO;

namespace Task310.Services.Interfaces
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
