using Asp.Versioning;
using Microsoft.AspNetCore.Mvc;
using Publisher.Dto.Request.CreateTo;
using Publisher.Dto.Request.UpdateTo;
using Publisher.Dto.Response;
using Publisher.Models;
using Publisher.Services.Interfaces;
namespace Publisher.Controllers;

[Route("api/v{version:apiVersion}/issues")]
[ApiVersion("1.0")]
[ApiController]
public class IssueController(IIssueService service) : ControllerBase
{
    [HttpGet("{id:long}")]
    public async Task<ActionResult<Issue>> GetIssue(long id)
    {
        return Ok(await service.GetIssueById(id));
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<Issue>>> GetIssues()
    {
        return Ok(await service.GetIssues());
    }

    [HttpPost]
    public async Task<ActionResult<IssueResponseTo>> CreateIssue(CreateIssueRequestTo createIssueRequestTo)
    {
        var addedIssue = await service.CreateIssue(createIssueRequestTo);
        return CreatedAtAction(nameof(GetIssue), new { id = addedIssue.Id }, addedIssue);
    }

    [HttpDelete("{id:long}")]
    public async Task<IActionResult> DeleteIssue(long id)
    {
        await service.DeleteIssue(id);
        return NoContent();
    }

    [HttpPut]
    public async Task<ActionResult<IssueResponseTo>> UpdateIssue(UpdateIssueRequestTo updateIssueRequestTo)
    {
        return Ok(await service.UpdateIssue(updateIssueRequestTo));
    }
}