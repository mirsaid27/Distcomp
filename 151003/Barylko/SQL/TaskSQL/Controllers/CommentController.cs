using Asp.Versioning;
using Microsoft.AspNetCore.Mvc;
using TaskREST.Dto.Request.CreateTo;
using TaskREST.Dto.Request.UpdateTo;
using TaskREST.Dto.Response;
using TaskREST.Models;
using TaskREST.Services.Interfaces;

namespace TaskREST.Controllers;

[Route("api/v{version:apiVersion}/comments")]
[ApiVersion("1.0")]
[ApiController]
public class CommentController(ICommentService service) : ControllerBase
{
    [HttpGet("{id:long}")]
    public async Task<ActionResult<Comment>> GetComment(long id)
    {
        return Ok(await service.GetCommentById(id));
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<Comment>>> GetComments()
    {
        return Ok(await service.GetComments());
    }

    [HttpPost]
    public async Task<ActionResult<CommentResponseTo>> CreateComment(CreateCommentRequestTo createCommentRequestTo)
    {
        var addedComment = await service.CreateComment(createCommentRequestTo);
        return CreatedAtAction(nameof(GetComment), new { id = addedComment.id }, addedComment);
    }

    [HttpDelete("{id:long}")]
    public async Task<IActionResult> DeleteComment(long id)
    {
        await service.DeleteComment(id);
        return NoContent();
    }

    [HttpPut]
    public async Task<ActionResult<CommentResponseTo>> UpdateComment(UpdateCommentRequestTo updateCommentRequestTo)
    {
        return Ok(await service.UpdateComment(updateCommentRequestTo));
    }
}