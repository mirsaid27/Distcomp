using Service.DTO.Request;
using Service.DTO.Response;
using Service.DTO.Request.Editor;
using Service.DTO.Response.Editor;

namespace Service.Interfaces;

public interface IEditorService
{
    public Task<EditorResponseToGetById?> CreateEditor(EditorRequestToCreate model);
    public Task<IEnumerable<EditorResponseToGetById?>?> GetEditors(EditorRequestToGetAll request);
    public Task<EditorResponseToGetById?> GetEditorById(EditorRequestToGetById request);
    public Task<EditorResponseToGetById?> GetEditorByArticleId(EditorRequestToGetByArticleId request);
    public Task<EditorResponseToGetById?> UpdateEditor(EditorRequestToFullUpdate model);
    public Task<EditorResponseToGetById?> DeleteEditor(EditorRequestToDeleteById request);
}