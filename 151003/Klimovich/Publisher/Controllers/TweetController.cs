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
    [Route("api/v1.0/tweets")]
    [ApiController]
    public class TweetController : ControllerBase
    {
        private readonly IServiceBase _context;
        private readonly IDistributedCache _cache;

        public TweetController(IServiceBase context, IDistributedCache cache)
        {
            _context = context;
            _cache = cache;
        }

        [HttpGet]
        public IActionResult GetTweets()
        {
            return StatusCode(200, _context.GetTweets());
        }

        [HttpGet("{id}")]
        public IActionResult GetTweet(int id)
        {
            var res = _cache.GetString("tweet_" + id.ToString());
            if (res != null)
                return StatusCode(200, JsonConvert.DeserializeObject<TweetResponceTo>(res));
            else
            {
                var resWithoutCache = _context.GetTweet(id);
                _cache.SetString("tweet_" + resWithoutCache.id.ToString(), JsonConvert.SerializeObject(resWithoutCache), new DistributedCacheEntryOptions
                {
                    AbsoluteExpirationRelativeToNow = TimeSpan.FromMinutes(5)
                });
                return StatusCode(200, resWithoutCache);
            }
        }

        [HttpPut]
        public IActionResult UpdateTweet([FromBody] TweetRequestTo tweet)
        {
            try
            {
                var newTweet = _context.UpdateTweet(tweet);
                _cache.SetString("tweet_" + newTweet.id.ToString(), JsonConvert.SerializeObject(newTweet), new DistributedCacheEntryOptions
                {
                    AbsoluteExpirationRelativeToNow = TimeSpan.FromMinutes(5)
                });
                return StatusCode(200, newTweet);
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
        public IActionResult PostTweet([FromBody] TweetRequestTo tweet)
        {
            try
            {
                return StatusCode(201, _context.CreateTweet(tweet));
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
        public IActionResult DeleteTweet(int id)
        {
            int res = _context.DeleteTweet(id);
            if (res == 0)
                return StatusCode(400);
            else
            {
                _cache.Remove("tweet_" + id.ToString());
                return StatusCode(204);
            }
        }
    }
}
