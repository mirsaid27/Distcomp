using Task310.DTO.RequestDTO;
using Task310.DTO.ResponseDTO;

namespace Task310.Services.Interfaces
{
    public interface INewsService
    {
        Task<IEnumerable<NewsResponseDto>> GetNewsAsync();

        Task<NewsResponseDto> GetNewsByIdAsync(long id);

        Task<NewsResponseDto> CreateNewsAsync(NewsRequestDto issue);

        Task<NewsResponseDto> UpdateNewsAsync(NewsRequestDto issue);

        Task DeleteNewsAsync(long id);
    }
}
