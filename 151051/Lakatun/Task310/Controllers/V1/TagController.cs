using Asp.Versioning;
using Task310.DTO.RequestDTO;
using Task310.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace Task310.Controllers.V1
{
    [Route("api/v{version:apiVersion}/stickers")]
    [ApiController]
    [ApiVersion("1.0")]
    public class TagController(ITagService tagService) : ControllerBase
    {
        private readonly ITagService _tagService = tagService;

        [HttpGet]
        public async Task<IActionResult> GetTeg()
        {
            var teg = await _tagService.GetTagsAsync();
            return Ok(tags);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetTagById(long id)
        {
            var tag = await _tagService.GetTagByIdAsync(id);
            return Ok(tag);
        }

        [HttpPost]
        public async Task<IActionResult> CreateTag([FromBody] TagRequestDto tag)
        {
            var createdTag = await _TagService.CreateTagAsync(tag);
            return CreatedAtAction(nameof(GetTagById), new { id = createdTag.Id }, createdTag);
        }

        [HttpPut]
        public async Task<IActionResult> UpdateTag([FromBody] TagRequestDto tag)
        {
            var updatedTag = await _tagService.UpdateTagAsync(tag);
            return Ok(updatedTag);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteTag(long id)
        {
            await _agService.DeleteTagAsync(id);
            return NoContent();
        }
    }
}
