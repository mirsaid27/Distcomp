using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Publisher.Controllers.V1_0.Common;
using Publisher.Entity.Db;
using Publisher.Entity.DTO.RequestTO;
using Publisher.Entity.DTO.ResponseTO;
using Publisher.Service.Interface;
using System.Net;

namespace Publisher.Controllers.V1_0
{
    [Route("api/v1.0/editors")]
    [ApiController]
    public class EditorController(IEditorService EditorService, ILogger<EditorController> Logger, IMapper Mapper) :
        AbstractController<Editor, EditorRequestTO, EditorResponseTO>(EditorService, Logger, Mapper)
    {
        [HttpGet]
        [Route("storys/{id:int}")]
        public async Task<JsonResult> GetByStoryID([FromRoute] int id)
        {
            EditorResponseTO? response = null;
            Logger.LogInformation("Getting editor by story ID: {id}", id);

            try
            {
                response = await EditorService.GetByStoryID(id);
            }
            catch (Exception ex)
            {
                Logger.LogError("Error getting editor by story ID {ex}", ex);
                Response.StatusCode = (int)HttpStatusCode.BadRequest;
            }

            return Json(response);
        }
    }
}
