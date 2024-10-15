using Task310.DTO.RequestDTO;
using Task310.DTO.ResponseDTO;

namespace Task310.Services.Interfaces
{
    public interface ICreatorService
    {
        Task<IEnumerable<CreatorResponseDto>> GetCreatorsAsync();

        Task<CreatorResponseDto> GetCreatorByIdAsync(long id);

        Task<CreatorResponseDto> CreateCreatorAsync(CreatorRequestDto creator);

        Task<CreatorResponseDto> UpdateCreatorAsync(CreatorRequestDto creator);

        Task DeleteCreatorAsync(long id);
    }
}
