using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;

namespace dc_rest.Services.Interfaces;

public interface ICreatorService
{
    Task<IEnumerable<CreatorResponseDto>> GetCreatorsAsync();

    Task<CreatorResponseDto> GetCreatorByIdAsync(long id);
    
    Task<CreatorResponseDto> CreateCreatorAsync(CreatorRequestDto creator);

    Task<CreatorResponseDto> UpdateCreatorAsync(CreatorRequestDto creator);

    Task DeleteCreatorAsync(long id);
}