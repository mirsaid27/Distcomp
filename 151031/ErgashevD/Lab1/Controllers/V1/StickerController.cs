using Asp.Versioning;
using Lab1.DTO.RequestDTO;
using Lab1.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace Lab1.Controllers.V1
{
    [Route("api/v{version:apiVersion}/stickers")]
    [ApiController]
    [ApiVersion("1.0")]
    public class StickerController(IStickerService stickerService) : ControllerBase
    {
        private readonly IStickerService _stickerService = stickerService;

        [HttpGet]
        public async Task<IActionResult> GetStickers()
        {
            var stickers = await _stickerService.GetStickersAsync();
            return Ok(stickers);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetStickerById(long id)
        {
            var sticker = await _stickerService.GetStickerByIdAsync(id);
            return Ok(sticker);
        }

        [HttpPost]
        public async Task<IActionResult> CreateSticker([FromBody] StickerRequestDto sticker)
        {
            var createdSticker = await _stickerService.CreateStickerAsync(sticker);
            return CreatedAtAction(nameof(GetStickerById), new { id = createdSticker.Id }, createdSticker);
        }

        [HttpPut]
        public async Task<IActionResult> UpdateSticker([FromBody] StickerRequestDto sticker)
        {
            var updatedSticker = await _stickerService.UpdateStickerAsync(sticker);
            return Ok(updatedSticker);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteSticker(long id)
        {
            await _stickerService.DeleteStickerAsync(id);
            return NoContent();
        }
    }
}
