using Newtonsoft.Json;
using Confluent.Kafka;
using Discussion.Services.Interfaces;
using Confluent.Kafka.Admin;
using Discussion.Models.DTO.RequestTo;

namespace Discussion.Services.Kafka
{
    public class KafkaService : IKafkaService
    {
        private ICommentService _commentService;
        private ConsumerConfig config = new ConsumerConfig
        {
            BootstrapServers = "kafka:9092",
            GroupId = "foo",
        };

        public KafkaService(ICommentService commentService)
        {
            _commentService = commentService;
            using (var adminClient = new AdminClientBuilder(config).Build())
            {
                try
                {
                    adminClient.CreateTopicsAsync(new TopicSpecification[] { new TopicSpecification { Name = "InTopic" } }).Wait();
                    Console.WriteLine("Created Topic");
                }
                catch (AggregateException e) { }
                try
                {
                    adminClient.CreateTopicsAsync(new TopicSpecification[] { new TopicSpecification { Name = "OutTopic" } }).Wait();
                    Console.WriteLine("Created Topic");
                }
                catch (AggregateException e) { }
            }
        }

        public void StartConsuming()
        {
            CancellationTokenSource cts = new CancellationTokenSource();
            using (var consumer = new ConsumerBuilder<Ignore, string>(config).Build())
            {
                consumer.Subscribe("InTopic");
                try
                {
                    while (true)
                    {
                        var consumeResult = consumer.Consume(cts.Token);
                        if (consumeResult != null)
                        {
                            ProcessMessage(consumeResult);
                        }
                    }
                }
                finally
                {
                    consumer.Close();
                }
            }
        }

        private void ProcessMessage(ConsumeResult<Ignore, string> _message)
        {
            var message = JsonConvert.DeserializeObject<KafkaMessageTemplate>(_message.Value);
            switch (message.Command)
            {
                case "create":
                    {
                        var data = JsonConvert.DeserializeObject<CommentRequestTo>(message.Data);
                        _commentService.CreateComment(data);
                        break;
                    }
                case "get":
                    {
                        var data = JsonConvert.DeserializeObject<Dictionary<string, int>>(message.Data);
                        var res = _commentService.GetComment(data["id"]);
                        Produce(res.tweetId.ToString(), "get_response", JsonConvert.SerializeObject(res));
                        break;
                    }
                case "getAll":
                    {
                        var res = _commentService.GetComments();
                        Produce("extra", "getAll_response", JsonConvert.SerializeObject(res));
                        break;
                    }
                case "update":
                    {
                        var data = JsonConvert.DeserializeObject<CommentRequestTo>(message.Data);
                        var res = _commentService.UpdateComment(data);
                        Produce(res.tweetId.ToString(), "update_response", JsonConvert.SerializeObject(res));
                        break;
                    }
                case "delete":
                    {
                        var data = JsonConvert.DeserializeObject<Dictionary<string, int>>(message.Data);
                        var res = _commentService.DeleteComment(data["id"]);
                        Produce("extra", "delete_response", JsonConvert.SerializeObject(res));
                        break;
                    }
            }
        }

        private void Produce(string key, string command, string data)
        {
            KafkaMessageTemplate template = new KafkaMessageTemplate();
            template.Command = command;
            template.Data = data;
            using (var producer = new ProducerBuilder<string, string>(config).Build())
            {
                producer.Produce("OutTopic", new Message<string, string>
                {
                    Value = JsonConvert.SerializeObject(template),
                    Key = key,
                });
                producer.Flush();
            }
        }
    }
}
