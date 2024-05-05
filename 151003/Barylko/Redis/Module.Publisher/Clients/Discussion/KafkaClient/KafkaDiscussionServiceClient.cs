using Publisher.Clients.Discussion.Dto.Message;
using Publisher.Clients.Discussion.Dto.Request;
using Publisher.Clients.Discussion.Dto.Response;
using TaskRedis.ServiceDefaults.Kafka.SyncClient;
namespace Publisher.Clients.Discussion.KafkaClient;

public class KafkaDiscussionServiceClient(KafkaSyncClient<InTopicMessage, OutTopicMessage> client) : IDiscussionService
{
    public async Task<DiscussionCommentResponseTo> GetComment(long id)
    {
        OutTopicMessage outMessage = await client.ProduceAsync(
            new InTopicMessage(OperationType.GetById, new DiscussionCommentRequestTo(id)));

        if (!outMessage.IsSuccess)
            throw new Exception(outMessage.ErrorMessage);

        return outMessage.Result.First();
    }

    public async Task<IEnumerable<DiscussionCommentResponseTo>> GetComments()
    {
        OutTopicMessage outMessage = await client.ProduceAsync(
            new InTopicMessage(OperationType.GetAll, new DiscussionCommentRequestTo()));

        if (!outMessage.IsSuccess)
            throw new Exception(outMessage.ErrorMessage);

        return outMessage.Result;
    }

    public async Task<DiscussionCommentResponseTo> CreateComment(DiscussionCommentRequestTo createCommentRequestTo)
    {
        OutTopicMessage outMessage = await client.ProduceAsync(
            new InTopicMessage(OperationType.Create, createCommentRequestTo));

        if (!outMessage.IsSuccess)
            throw new Exception(outMessage.ErrorMessage);

        return outMessage.Result.First();
    }

    public async Task DeleteComment(long id)
    {
        OutTopicMessage outMessage = await client.ProduceAsync(
            new InTopicMessage(OperationType.Delete, new DiscussionCommentRequestTo(id)));

        if (!outMessage.IsSuccess)
            throw new Exception(outMessage.ErrorMessage);
    }

    public async Task<DiscussionCommentResponseTo> UpdateComment(DiscussionCommentRequestTo postRequestTo)
    {
        OutTopicMessage outMessage = await client.ProduceAsync(
            new InTopicMessage(OperationType.Update, postRequestTo));

        if (!outMessage.IsSuccess)
            throw new Exception(outMessage.ErrorMessage);

        return outMessage.Result.First();
    }
}
