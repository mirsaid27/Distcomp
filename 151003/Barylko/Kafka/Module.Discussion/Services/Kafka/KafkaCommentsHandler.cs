using Discussion.Dto.Message;
using Discussion.Dto.Response;
using Discussion.Services.Interfaces;
using TaskKafka.ServiceDefaults.Kafka.Consumer;
using TaskKafka.ServiceDefaults.Kafka.Producer;
namespace Discussion.Services.Kafka;

public sealed class KafkaCommentsHandler(
    KafkaMessageProducer<string, OutTopicMessage> producer,
    ICommentService commentService) : IKafkaHandler<string, InTopicMessage>
{
    public async Task HandleAsync(string key, InTopicMessage value)
    {
        OutTopicMessage response;
        try
        {
            response = await HandleOperation(value);
        }
        catch (Exception e)
        {
            response = new OutTopicMessage(value.OperationType, [], e.Message);
        }

        await producer.ProduceAsync(key, response);
    }

    private async Task<OutTopicMessage> HandleOperation(InTopicMessage value) => value.OperationType switch
    {
        OperationType.GetById => await HandleGetById(value),
        OperationType.GetAll => await HandleGetAll(),
        OperationType.Create => await HandleCreate(value),
        OperationType.Delete => await HandleDelete(value),
        OperationType.Update => await HandleUpdate(value),
        _ => throw new ArgumentOutOfRangeException()
    };
    
    private async Task<OutTopicMessage> HandleGetById(InTopicMessage value)
    {
        CommentResponseTo comment = await commentService.GetCommentById(value.Message.Id);
        return new OutTopicMessage(value.OperationType, [comment]);
    }
    
    private async Task<OutTopicMessage> HandleGetAll()
    {
        var comments = await commentService.GetComments();
        return new OutTopicMessage(OperationType.GetAll, comments.ToList());
    }
    
    private async Task<OutTopicMessage> HandleCreate(InTopicMessage value)
    {
        CommentResponseTo comment = await commentService.CreateComment(value.Message);
        return new OutTopicMessage(value.OperationType, [comment]);
    }
    
    private async Task<OutTopicMessage> HandleDelete(InTopicMessage value)
    {
        await commentService.DeleteComment(value.Message.Id);
        return new OutTopicMessage(value.OperationType, []);
    }
    
    private async Task<OutTopicMessage> HandleUpdate(InTopicMessage value)
    {
        CommentResponseTo comment = await commentService.UpdateComment(value.Message);
        return new OutTopicMessage(value.OperationType, [comment]);
    }
}
