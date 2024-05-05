using Lab1.DTO.RequestDTO;
using Lab1.DTO.ResponseDTO;

namespace Lab1.Services.Interfaces
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
