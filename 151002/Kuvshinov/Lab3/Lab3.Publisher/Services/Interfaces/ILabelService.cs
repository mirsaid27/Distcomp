using Lab3.Publisher.DTO.RequestDTO;
using Lab3.Publisher.DTO.ResponseDTO;

namespace Lab3.Publisher.Services.Interfaces;

public interface IStickerService
{
    Task<IEnumerable<StickerResponseDto>> GetStickersAsync();

    Task<StickerResponseDto> GetStickerByIdAsync(long id);

    Task<StickerResponseDto> CreateStickerAsync(StickerRequestDto sticker);

    Task<StickerResponseDto> UpdateStickerAsync(StickerRequestDto sticker);

    Task DeleteStickerAsync(long id);
}