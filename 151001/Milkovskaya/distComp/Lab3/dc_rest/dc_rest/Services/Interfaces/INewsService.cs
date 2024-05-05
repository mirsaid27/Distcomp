using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;

namespace dc_rest.Services.Interfaces;

public interface INewsService
{
    Task<IEnumerable<NewsResponseDto>> GetNewsAsync();

    Task<NewsResponseDto> GetNewsByIdAsync(long id);

    Task<NewsResponseDto> CreateNewsAsync(NewsRequestDto issue);

    Task<NewsResponseDto> UpdateNewsAsync(NewsRequestDto issue);

    Task DeleteNewsAsync(long id);
}