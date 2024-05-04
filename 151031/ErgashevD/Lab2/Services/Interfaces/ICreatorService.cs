using Lab2.DTO.RequestDTO;
using Lab2.DTO.ResponseDTO;

namespace Lab2.Services.Interfaces
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
