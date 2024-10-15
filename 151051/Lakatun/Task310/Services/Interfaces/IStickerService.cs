using Task310.DTO.RequestDTO;
using Task310.DTO.ResponseDTO;

namespace Task310.Services.Interfaces
{
    public interface ITagService
    {
        Task<IEnumerable<TagResponseDto>> GetTagsAsync();

        Task<TagResponseDto> GetTagByIdAsync(long id);

        Task<TagResponseDto> CreateTagAsync(TagRequestDto tag);

        Task<TagResponseDto> UpdateTagAsync(TagRequestDto tag);

        Task DeleteTagAsync(long id);
    }
}
