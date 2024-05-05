using Lab1.DTO.RequestDTO;
using Lab1.DTO.ResponseDTO;

namespace Lab1.Services.Interfaces
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
