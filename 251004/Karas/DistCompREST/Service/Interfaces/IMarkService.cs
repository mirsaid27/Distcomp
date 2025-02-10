using Service.DTO.Request;
using Service.DTO.Response;
using Service.DTO.Request.Mark;
using Service.DTO.Response.Mark;

namespace Service.Interfaces;

public interface IMarkService
{
    public Task<MarkResponseToGetById?> CreateMark(MarkRequestToCreate model);
    public Task<IEnumerable<MarkResponseToGetById?>?> GetMarks(MarkRequestToGetAll request);
    public Task<MarkResponseToGetById?> GetMarkById(MarkRequestToGetById request);
    public Task<MarkResponseToGetById?> UpdateMark(MarkRequestToFullUpdate model);
    public Task<MarkResponseToGetById?> DeleteMark(MarkRequestToDeleteById request);
}