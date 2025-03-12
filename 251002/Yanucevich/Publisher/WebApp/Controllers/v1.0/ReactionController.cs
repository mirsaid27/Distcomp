using System;
using System.Reflection.Metadata;
using Asp.Versioning;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using Settings;
using SocialNet.Abstractions;

namespace Discussion.Controllers;

[Route("reactions")]
[ApiVersion("1.0")]
public class ReactionController : MediatrController
{
    private readonly HttpClient _httpClient;
    private readonly DiscussionSettings _discussionSettings;

    public ReactionController(
        IMediator mediator,
        HttpClient httpClient,
        IOptions<DiscussionSettings> discussionSettings
    )
        : base(mediator)
    {
        _httpClient = httpClient;
        _discussionSettings = discussionSettings.Value;
    }

    [HttpPost]
    public async Task<IActionResult> CreateReaction([FromBody] object requestBody)
    {
        var microserviceUrl = $"{_discussionSettings.DiscussionUrl}/api/v1.0/reactions/";

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
    public async Task<IActionResult> GetReactions()
    {
        var microserviceUrl = $"{_discussionSettings.DiscussionUrl}/api/v1.0/reactions/";

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
    public async Task<IActionResult> GetReactionByIdQuery(string id)
    {
        var microserviceUrl = $"{_discussionSettings.DiscussionUrl}/api/v1.0/reactions/{id}";

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
    public async Task<IActionResult> UpdateReaction([FromBody] object requestBody)
    {
        var microserviceUrl = $"{_discussionSettings.DiscussionUrl}/api/v1.0/reactions/";

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
    public async Task<IActionResult> DeleteReaction(string id)
    {
        var microserviceUrl = $"{_discussionSettings.DiscussionUrl}/api/v1.0/reactions/{id}";

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
