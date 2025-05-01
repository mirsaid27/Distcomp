using Core;
using DTO.requests;
using DTO.responces;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace rv_lab_1.controllers
{
    [Route("api/v1.0/notes")]
    [ApiController]
    public class NoteController : ControllerBase
    {
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly string _remoteUrl = "http://localhost:24130/api/v1.0/notes"; // URL удаленного сервера

        public NoteController(IHttpClientFactory httpClientFactory)
        {
            _httpClientFactory = httpClientFactory;
        }


        [HttpGet("{id:long}")]
        public async Task<ActionResult<NoteResponseTo?>> GetByIdAsync(long id)
        {
            var client = _httpClientFactory.CreateClient();
            var response = await client.GetAsync($"{_remoteUrl}/{id}");

            if (!response.IsSuccessStatusCode)
            {
                return StatusCode((int)response.StatusCode);
            }

            var result = await response.Content.ReadFromJsonAsync<NoteResponseTo>();
            return Ok(result);
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<NoteResponseTo>>> GetAllAsync()
        {
            var client = _httpClientFactory.CreateClient();
            var response = await client.GetAsync(_remoteUrl);

            if (!response.IsSuccessStatusCode)
            {
                return StatusCode((int)response.StatusCode);
            }

            var result = await response.Content.ReadFromJsonAsync<IEnumerable<NoteResponseTo>>();
            return Ok(result);
        }

        [HttpPost]
        public async Task<ActionResult<NoteResponseTo>> PostAsync([FromBody] NoteRequestTo requestTo)
        {
            var client = _httpClientFactory.CreateClient();
            var response = await client.PostAsJsonAsync(_remoteUrl, requestTo);

            if (!response.IsSuccessStatusCode)
            {
                return StatusCode((int)response.StatusCode);
            }

            var result = await response.Content.ReadFromJsonAsync<NoteResponseTo>(); 
            return Created(string.Empty, result);
        }

        [HttpPut]
        public async Task<ActionResult> Put([FromBody] Note note)
        {
            Console.WriteLine($"Received: id={note.Id}, storyId={note.StoryId}, content={note.Content}");
            var client = _httpClientFactory.CreateClient();
            var response = await client.PutAsJsonAsync(_remoteUrl, note);

            if (!response.IsSuccessStatusCode)
            {
                return StatusCode((int)response.StatusCode);
            }
            var result = await response.Content.ReadFromJsonAsync<NoteResponseTo>();
            return Ok(result);
        }

        //[HttpPut("{id}")]
        //public async Task<ActionResult> PutId(long id, [FromBody] NoteRequestTo requestTo)
        //{
        //    var client = _httpClientFactory.CreateClient();
        //    var response = await client.PutAsJsonAsync($"{_remoteUrl}/{id}", requestTo);

        //    if (!response.IsSuccessStatusCode)
        //    {
        //        return StatusCode((int)response.StatusCode);
        //    }
        //    return Ok();
        //}

        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(long id)
        {
            var client = _httpClientFactory.CreateClient();
            var response = await client.DeleteAsync($"{_remoteUrl}/{id}");

            if (!response.IsSuccessStatusCode)
            {
                return StatusCode((int)response.StatusCode);
            }

            return NoContent();
        }
    }
}
