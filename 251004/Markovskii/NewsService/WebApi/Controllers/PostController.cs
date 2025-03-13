using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using WebApi.Settings;

namespace WebApi.Controllers;

[ApiController]
[Route("/api/v1.0/posts")]
public class PostController : ControllerBase
{
    private readonly HttpClient _httpClient;
    private readonly DiscussionSettings _discussionSettings;

    public PostController(
        HttpClient httpClient,
        IOptions<DiscussionSettings> discussionSettings
    )
    {
        _httpClient = httpClient;
        _discussionSettings = discussionSettings.Value;
    }

    [HttpPost]
    public async Task<IActionResult> CreatePost([FromBody] object requestBody)
    {
        var microserviceUrl = $"{_discussionSettings.DiscussionUrl}/api/v1.0/posts/";

        var response = await _httpClient.PostAsJsonAsync(microserviceUrl, requestBody);

        if (response.IsSuccessStatusCode)
        {
            var content = await response.Content.ReadAsStringAsync();
            return StatusCode((int)response.StatusCode, content);
        }
        else
        {
            return StatusCode((int)response.StatusCode, await response.Content.ReadAsStringAsync());
        }
    }

    [HttpGet]
    public async Task<IActionResult> GetPosts()
    {
        var microserviceUrl = $"{_discussionSettings.DiscussionUrl}/api/v1.0/posts/";

        var response = await _httpClient.GetAsync(microserviceUrl);

        if (response.IsSuccessStatusCode)
        {
            var content = await response.Content.ReadAsStringAsync();
            return StatusCode((int)response.StatusCode, content);
        }
        else
        {
            return StatusCode((int)response.StatusCode, await response.Content.ReadAsStringAsync());
        }
    }

    [HttpGet("{id}")]
    public async Task<IActionResult> GetPostByIdQuery(string id)
    {
        var microserviceUrl = $"{_discussionSettings.DiscussionUrl}/api/v1.0/posts/{id}";

        var response = await _httpClient.GetAsync(microserviceUrl);

        if (response.IsSuccessStatusCode)
        {
            var content = await response.Content.ReadAsStringAsync();
            return StatusCode((int)response.StatusCode, content);
        }
        else
        {
            return StatusCode((int)response.StatusCode, await response.Content.ReadAsStringAsync());
        }
    }

    [HttpPut]
    public async Task<IActionResult> UpdatePost([FromBody] object requestBody)
    {
        var microserviceUrl = $"{_discussionSettings.DiscussionUrl}/api/v1.0/posts/";

        var response = await _httpClient.PutAsJsonAsync(microserviceUrl, requestBody);

        if (response.IsSuccessStatusCode)
        {
            var content = await response.Content.ReadAsStringAsync();
            return StatusCode((int)response.StatusCode, content);
        }
        else
        {
            return StatusCode((int)response.StatusCode, await response.Content.ReadAsStringAsync());
        }
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeletePost(string id)
    {
        var microserviceUrl = $"{_discussionSettings.DiscussionUrl}/api/v1.0/posts/{id}";

        var response = await _httpClient.DeleteAsync(microserviceUrl);

        if (response.IsSuccessStatusCode)
        {
            var content = await response.Content.ReadAsStringAsync();
            return StatusCode((int)response.StatusCode, content);
        }
        else
        {
            return StatusCode((int)response.StatusCode, await response.Content.ReadAsStringAsync());
        }
    }   
}