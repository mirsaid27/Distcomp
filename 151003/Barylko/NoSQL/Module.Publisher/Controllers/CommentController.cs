using System.Globalization;
using Asp.Versioning;
using Microsoft.AspNetCore.Mvc;
using Publisher.Dto.Request.CreateTo;
using Publisher.Dto.Request.UpdateTo;
using Publisher.Dto.Response;
using Publisher.Services.Interfaces;
namespace Publisher.Controllers;

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
    public async Task<ActionResult<CommentResponseTo>> CreateComment(CreateCommentRequestTo createCommentRequestTo)
    {
        var country = GetCountryFromRequest();
        CommentResponseTo addedComment = await service.CreateComment(createCommentRequestTo, country);
        return CreatedAtAction(nameof(GetComment), new { id = addedComment.Id }, addedComment);
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
        var country = GetCountryFromRequest();
        return Ok(await service.UpdateComment(updateCommentRequestTo, country));
    }

    private string GetCountryFromRequest() =>
        Request.GetTypedHeaders().AcceptLanguage
            .MaxBy(x => x.Quality ?? 1)
            ?.Value.ToString() ?? CultureInfo.CurrentCulture.Name;
}
