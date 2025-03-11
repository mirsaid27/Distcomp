using Publisher.DTO.RequestDTO;
using Publisher.DTO.ResponseDTO;

namespace Publisher.Services.Interfaces;

public interface INoticeService
{
    Task<IEnumerable<NoticeResponseDTO>> GetNoticesAsync();

    Task<NoticeResponseDTO> GetNoticeByIdAsync(long id);

    Task<NoticeResponseDTO> CreateNoticeAsync(NoticeRequestDTO notice);

    Task<NoticeResponseDTO> UpdateNoticeAsync(NoticeRequestDTO notice);

    Task DeleteNoticeAsync(long id);
}