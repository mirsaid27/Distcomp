using Lab1.DTO.RequestDTO;
using Lab1.DTO.ResponseDTO;

namespace Lab1.Services.Interfaces
{
    public interface IStickerService
    {
        Task<IEnumerable<StickerResponseDto>> GetStickersAsync();

        Task<StickerResponseDto> GetStickerByIdAsync(long id);

        Task<StickerResponseDto> CreateStickerAsync(StickerRequestDto sticker);

        Task<StickerResponseDto> UpdateStickerAsync(StickerRequestDto sticker);

        Task DeleteStickerAsync(long id);
    }
}
