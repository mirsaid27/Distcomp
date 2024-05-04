using Lab2.DTO.RequestDTO;
using Lab2.DTO.ResponseDTO;

namespace Lab2.Services.Interfaces
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
