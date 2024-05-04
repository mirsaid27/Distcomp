using Lab5.Publisher.DTO.RequestDTO;
using Lab5.Publisher.DTO.ResponseDTO;

namespace Lab5.Publisher.Services.Interfaces;

public interface ICreatorService
{
    Task<IEnumerable<CreatorResponseDto>> GetCreatorsAsync();

    Task<CreatorResponseDto> GetCreatorByIdAsync(long id);

    Task<CreatorResponseDto> CreateCreatorAsync(CreatorRequestDto creator);

    Task<CreatorResponseDto> UpdateCreatorAsync(CreatorRequestDto creator);

    Task DeleteCreatorAsync(long id);
}