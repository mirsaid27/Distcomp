using Asp.Versioning;
using Microsoft.AspNetCore.Mvc;
using TaskREST.Dto.Request.CreateTo;
using TaskREST.Dto.Request.UpdateTo;
using TaskREST.Dto.Response;
using TaskREST.Models;
using TaskREST.Services.Interfaces;

namespace TaskREST.Controllers;

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
        return CreatedAtAction(nameof(GetUser), new { id = addedUser.id }, addedUser);
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