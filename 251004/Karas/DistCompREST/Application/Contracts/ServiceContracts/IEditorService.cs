using Application.Dto.Request;
using Application.Dto.Response;

namespace Application.Contracts.ServiceContracts;

public interface IEditorService
{
    Task<IEnumerable<EditorResponseDto>> GetEditorsAsync();

    Task<EditorResponseDto> GetEditorByIdAsync(long id);

    Task<EditorResponseDto> CreateEditorAsync(EditorRequestDto editor);

    Task<EditorResponseDto> UpdateEditorAsync(EditorRequestDto editor);

    Task DeleteEditorAsync(long id);
}