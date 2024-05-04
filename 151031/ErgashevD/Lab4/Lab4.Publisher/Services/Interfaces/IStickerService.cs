using Lab4.Publisher.DTO.RequestDTO;
using Lab4.Publisher.DTO.ResponseDTO;

namespace Lab4.Publisher.Services.Interfaces;

public interface IStickerService
{
    Task<IEnumerable<StickerResponseDto>> GetStickersAsync();

    Task<StickerResponseDto> GetStickerByIdAsync(long id);

    Task<StickerResponseDto> CreateStickerAsync(StickerRequestDto sticker);

    Task<StickerResponseDto> UpdateStickerAsync(StickerRequestDto sticker);

    Task DeleteStickerAsync(long id);
}