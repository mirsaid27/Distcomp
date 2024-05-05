using Publisher.Models;
using Publisher.Models.DTOs.DTO;
using Publisher.Models.DTOs.ResponceTo;
using Publisher.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Caching.Distributed;
using Newtonsoft.Json;
using Npgsql;

namespace Publisher.Controllers
{
    [Route("api/v1.0/comments")]
    [ApiController]
    public class CommentController : ControllerBase
    {
        private readonly IServiceBase _context;
        private readonly IDistributedCache _cache;

        public CommentController(IServiceBase context, IDistributedCache cache)
        {
            _context = context;
            _cache = cache;
        }

        [HttpGet]
        public IActionResult GetComments()
        {
            return StatusCode(200, _context.GetComments());
        }

        [HttpGet("{id}")]
        public IActionResult GetComment(int id)
        {
            var res = _cache.GetString("comment_" + id.ToString());
            if (res != null)
                return StatusCode(200, JsonConvert.DeserializeObject<CommentResponceTo>(res));
            else
            {
                var resWithoutCache = _context.GetComment(id);
                _cache.SetString("comment_" + resWithoutCache.id.ToString(), JsonConvert.SerializeObject(resWithoutCache), new DistributedCacheEntryOptions
                {
                    AbsoluteExpirationRelativeToNow = TimeSpan.FromMinutes(5)
                });
                return StatusCode(200, resWithoutCache);
            }
            return StatusCode(200, _context.GetComment(id));

        }

        [HttpPut]
        public IActionResult UpdateComment([FromBody] CommentRequestTo comment)
        {
            try
            {
                var newComment = _context.UpdateComment(comment);
                _cache.SetString("comment_" + newComment.id.ToString(), JsonConvert.SerializeObject(newComment), new DistributedCacheEntryOptions
                {
                    AbsoluteExpirationRelativeToNow = TimeSpan.FromMinutes(5)
                });
                return StatusCode(200, newComment);
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
        public IActionResult PostComment([FromBody] CommentRequestTo comment)
        {
            try
            {
                var tweet = _context.GetTweet(comment.tweetId.Value);
                if(tweet == null)
                {
                    return StatusCode(400);
                }
                return StatusCode(201, _context.CreateComment(comment));
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
        public IActionResult DeleteComment(int id)
        {
            int res = _context.DeleteComment(id);
            if (res == 0)
                return StatusCode(400);
            else
            {
                _cache.Remove("comment_" + id.ToString());
                return StatusCode(204);
            }
        }
    }
}
