using Asp.Versioning;
using Microsoft.AspNetCore.Mvc;
using Publisher.Dto.Request.CreateTo;
using Publisher.Dto.Request.UpdateTo;
using Publisher.Dto.Response;
using Publisher.Models;
using Publisher.Services.Interfaces;
namespace Publisher.Controllers;

[Route("api/v{version:apiVersion}/users")]
[ApiVersion("1.0")]
[ApiController]
public class UserController(IUserService service) : ControllerBase
{
    [HttpGet("{id:long}")]
    public async Task<ActionResult<User>> GetUser(long id)
    {
        return Ok(await service.GetUserById(id));
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<User>>> GetUsers()
    {
        return Ok(await service.GetUsers());
    }

    [HttpPost]
    public async Task<ActionResult<UserResponseTo>> CreateUser(CreateUserRequestTo createUserRequestTo)
    {
        var addedUser = await service.CreateUser(createUserRequestTo);
        return CreatedAtAction(nameof(GetUser), new { id = addedUser.Id }, addedUser);
    }

    [HttpDelete("{id:long}")]
    public async Task<IActionResult> DeleteUser(long id)
    {
        await service.DeleteUser(id);
        return NoContent();
    }

    [HttpPut]
    public async Task<ActionResult<UserResponseTo>> UpdateUser(UpdateUserRequestTo updateUserRequestTo)
    {
        return Ok(await service.UpdateUser(updateUserRequestTo));
    }
}