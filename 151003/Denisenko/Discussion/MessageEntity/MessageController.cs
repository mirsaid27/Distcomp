using AutoMapper;
using Discussion.Common;
using Discussion.MessageEntity.Dto;
using Discussion.MessageEntity.Interface;
using Microsoft.AspNetCore.Mvc;
using System.Net;

namespace Discussion.MessageEntity
{
    [Route("api/v1.0/messages")]
    [ApiController]
    public class MessageController(IMessageService MessageService, ILogger<MessageController> Logger, IMapper Mapper)
        : AbstractController<Message, MessageRequestTO, MessageResponseTO>(MessageService, Logger, Mapper)
    {
        [HttpGet]
        [Route("{id:int}")]
        public override async Task<JsonResult> GetByID([FromRoute] int id)
        {
            Logger.LogInformation("Get {type} {id}", typeof(Message), id);
            var json = Json(null);
            json.StatusCode = (int)HttpStatusCode.OK;

            try
            {
                var response = await MessageService.GetByID(id);
                json.Value = response;
            }
            catch
            {
                Logger.LogError("ERROR getting {type} {id}", typeof(Message), id);
                json.Value = new MessageResponseTO(default, default, string.Empty, string.Empty);
                json.StatusCode = (int)HttpStatusCode.NotFound;
            }

            return json;
        }
    }
}
