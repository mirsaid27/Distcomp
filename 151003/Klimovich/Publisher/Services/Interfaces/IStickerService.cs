using DC.Models.DTOs.ResponceTo;
using Publisher.Models.DTOs.DTO;

namespace Publisher.Services.Interfaces
{
    public interface IStickerService
    {
        StickerResponceTo CreateSticker(StickerRequestTo item);
        List<StickerResponceTo> GetStickers();
        StickerResponceTo GetSticker(int id);
        StickerResponceTo UpdateSticker(StickerRequestTo item);
        int DeleteSticker(int id);
    }
}
