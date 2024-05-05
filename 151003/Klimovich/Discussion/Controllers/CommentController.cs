using Microsoft.AspNetCore.Mvc;


using Discussion.Models.DTO.RequestTo;
using Discussion.Services;

namespace Discussion.Controllers
{
    [Route("api/v1.0/comments")]
    [ApiController]
    public class CommentController : ControllerBase
    {
        private readonly IServiceBase _context;

        public CommentController(IServiceBase context)
        {
            _context = context;
        }

        [HttpGet]
        public IActionResult GetComments()
        {
            return StatusCode(200, _context.GetComments());
        }

        [HttpGet("{id}")]
        public IActionResult GetComment(int id)
        {
            var res = _context.GetComment(id);
            if(res == null)
            {
                return StatusCode(400);
            }
            return StatusCode(200, res);
        }

        [HttpPut]
        public IActionResult UpdateComment([FromBody] CommentRequestTo comment)
        {
            var res = _context.UpdateComment(comment);
            return StatusCode(200, res);
        }

        [HttpPost]
        public IActionResult PostComment([FromBody] CommentRequestTo comment)
        {
            var res = _context.CreateComment(comment);
            return StatusCode(201, res);
        }

        [HttpDelete("{id}")]
        public IActionResult DeleteComment(int id)
        {
            int res = _context.DeleteComment(id);
            if (res == 0)
                return StatusCode(400);
            else
                return StatusCode(204);
        }
    }
}
