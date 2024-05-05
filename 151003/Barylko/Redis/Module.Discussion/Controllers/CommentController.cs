using Asp.Versioning;
using Discussion.Dto.Request;
using Discussion.Dto.Response;
using Discussion.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;
namespace Discussion.Controllers;

[Route("api/v{version:apiVersion}/comments")]
[ApiVersion("1.0")]
[ApiController]
public class CommentController(ICommentService service) : ControllerBase
{
    [HttpGet("{id:long}")]
    public async Task<ActionResult<CommentResponseTo>> GetComment(long id)
    {
        return Ok(await service.GetCommentById(id));
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<CommentResponseTo>>> GetComments()
    {
        return Ok(await service.GetComments());
    }

    [HttpPost]
    public async Task<ActionResult<CommentResponseTo>> CreateComment(CommentRequestTo CommentRequestTo)
    {
        CommentResponseTo addedComment = await service.CreateComment(CommentRequestTo);
        return CreatedAtAction(nameof(GetComment), new { id = addedComment.Id }, addedComment);
    }

    [HttpDelete("{id:long}")]
    public async Task<IActionResult> DeleteComment(long id)
    {
        await service.DeleteComment(id);
        return NoContent();
    }

    [HttpPut]
    public async Task<ActionResult<CommentResponseTo>> UpdateComment(CommentRequestTo updateCommentRequestTo)
    {
        return Ok(await service.UpdateComment(updateCommentRequestTo));
    }
}