using Publisher.Models;
using Publisher.Models.DTOs.DTO;
using Publisher.Models.DTOs.ResponceTo;
using Publisher.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Caching.Distributed;
using Newtonsoft.Json;
using Npgsql;
using DC.Models.DTOs.ResponceTo;


namespace Publisher.Controllers
{
    [Route("api/v1.0/stickers")]
    [ApiController]
    public class StickersController : ControllerBase
    {
        private readonly IServiceBase _context;
        private readonly IDistributedCache _cache;

        public StickersController(IServiceBase context, IDistributedCache cache)
        {
            _context = context;
            _cache = cache;
        }

        [HttpGet]
        public IActionResult GetStickers()
        {
            return StatusCode(200, _context.GetStickers());
        }

        [HttpGet("{id}")]
        public IActionResult GetSticker(int id)
        {
            var res = _cache.GetString("sticker_" + id.ToString());
            if (res != null)
                return StatusCode(200, JsonConvert.DeserializeObject<StickerResponceTo>(res));
            else
            {
                var resWithoutCache = _context.GetSticker(id);
                _cache.SetString("sticker_" + resWithoutCache.id.ToString(), JsonConvert.SerializeObject(resWithoutCache), new DistributedCacheEntryOptions
                {
                    AbsoluteExpirationRelativeToNow = TimeSpan.FromMinutes(5)
                });
                return StatusCode(200, resWithoutCache);
            }
        }

        [HttpPut]
        public IActionResult UpdateSticker([FromBody] StickerRequestTo sticker)
        {
            try
            {
                var newSticker = _context.UpdateSticker(sticker);
                _cache.SetString("sticker_" + newSticker.id.ToString(), JsonConvert.SerializeObject(newSticker), new DistributedCacheEntryOptions
                {
                    AbsoluteExpirationRelativeToNow = TimeSpan.FromMinutes(5)
                });
                return StatusCode(200, newSticker);
            }
            catch (DbUpdateException ex)
            {
                string errorCode = "400";
                string hash = ex.Message.GetHashCode().ToString();
                errorCode += hash[0];
                errorCode += hash[1];
                Dictionary<string, string> error = new Dictionary<string, string>() {
                    { "errorMeassage", ex.Message},
                    { "errorCode", errorCode}
                };
                return StatusCode(400, error);
            }
        }

        [HttpPost]
        public IActionResult PostSticker([FromBody] StickerRequestTo sticker)
        {
            try
            {
                return StatusCode(201, _context.CreateSticker(sticker));
            }
            catch (DbUpdateException ex)
            {
                int statusCode = 400;
                if (ex.InnerException is PostgresException postgresException)
                {
                    if (postgresException.SqlState == "23505")
                    {
                        statusCode = 403;
                    }
                }
                string hash = ex.Message.GetHashCode().ToString();
                string errorCode = statusCode.ToString();
                errorCode += hash[0];
                errorCode += hash[1];
                Dictionary<string, string> error = new Dictionary<string, string>() {
                    { "errorMeassage", ex.Message},
                    { "errorCode", errorCode}
                };
                return StatusCode(statusCode, error);
            }
        }

        [HttpDelete("{id}")]
        public IActionResult DeleteSticker(int id)
        {
            int res = _context.DeleteSticker(id);
            if (res == 0)
                return StatusCode(400);
            else
            {
                _cache.Remove("sticker_" + id.ToString());
                return StatusCode(204);
            }
        }
    }
}
