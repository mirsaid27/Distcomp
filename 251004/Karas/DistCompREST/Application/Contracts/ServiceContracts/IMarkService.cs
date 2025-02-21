using Application.Dto.Request;
using Application.Dto.Response;

namespace Application.Contracts.ServiceContracts;

public interface IMarkService
{
    Task<IEnumerable<MarkResponseDto>> GetMarksAsync();

    Task<MarkResponseDto> GetMarkByIdAsync(long id);

    Task<MarkResponseDto> CreateMarkAsync(MarkRequestDto mark);

    Task<MarkResponseDto> UpdateMarkAsync(MarkRequestDto mark);

    Task DeleteMarkAsync(long id);
}