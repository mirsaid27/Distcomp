using Confluent.Kafka;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;
using Publisher.Models.DTOs.DTO;
using Publisher.Models.DTOs.ResponceTo;
using Publisher.Services.Interfaces;

namespace Publisher.Services.Remote
{
    public class CommentServiceKafka : ICommentService
    {
        private ConsumerConfig config = new ConsumerConfig
        {
            BootstrapServers = "kafka:9092",
            GroupId = "foo",
            AutoOffsetReset = AutoOffsetReset.Earliest
        };
        private CancellationTokenSource cts = new CancellationTokenSource();

        public CommentResponceTo CreateComment(CommentRequestTo item)
        {
            if (item.content.Length < 2)
            {
                throw new DbUpdateException();
            }
            var rand = new Random();
            item.id = rand.Next();
            Produce(item.tweetId.ToString(), "create", JsonConvert.SerializeObject(item));
            var res = new CommentResponceTo()
            {
                content = item.content,
                id = item.id.Value,
                tweetId = item.tweetId.Value,
                //country = "by",
            };
            return res;
        }

        public int DeleteComment(int id)
        {
            var data = new Dictionary<string, int>(){
                { "id", id }
            };
            Produce("extra", "delete", JsonConvert.SerializeObject(data));
            var resString = Consume();
            int res;
            if (resString != null)
            {
                var temp = JsonConvert.DeserializeObject<KafkaMessageTemplate>(resString);
                res = int.Parse(temp.Data);
            }
            else
                throw new Exception("tested");
            return res;
        }

        public CommentResponceTo GetComment(int id)
        {
            var data = new Dictionary<string, int>(){
                { "id", id }
            };
            Produce("extra", "get", JsonConvert.SerializeObject(data));
            var resString = Consume();
            CommentResponceTo res;
            if (resString != null)
            {
                var temp = JsonConvert.DeserializeObject<KafkaMessageTemplate>(resString);
                res = JsonConvert.DeserializeObject<CommentResponceTo>(temp.Data);
            }
            else
                throw new Exception("tested");
            return res;
        }

        public List<CommentResponceTo> GetComments()
        {
            Produce("extra", "getAll", "");
            var resString = Consume();
            List<CommentResponceTo> res;
            if (resString != null)
            {
                var temp = JsonConvert.DeserializeObject<KafkaMessageTemplate>(resString);
                res = JsonConvert.DeserializeObject<List<CommentResponceTo>>(temp.Data);
            }
            else
                throw new Exception("tested");
            return res;
        }

        public CommentResponceTo UpdateComment(CommentRequestTo item)
        {
            var key = item.tweetId != null ? item.tweetId.ToString().ToString() : "extra";
            Produce(key, "update", JsonConvert.SerializeObject(item));
            var resString = Consume();
            CommentResponceTo res;
            if (resString != null)
            {
                var temp = JsonConvert.DeserializeObject<KafkaMessageTemplate>(resString);
                res = JsonConvert.DeserializeObject<CommentResponceTo>(temp.Data);
            }
            else
                throw new Exception("tested");
            return res;
        }

        private string? Consume()
        {
            using (var consumer = new ConsumerBuilder<Ignore, string>(config).Build())
            {
                consumer.Subscribe("OutTopic");
                try
                {
                    var consumeResult = consumer.Consume(cts.Token);
                    return consumeResult.Value;
                }
                finally
                {
                    consumer.Close();
                }
            }
            return null;
        }

        private void Produce(string key, string command, string data)
        {
            KafkaMessageTemplate template = new KafkaMessageTemplate();
            template.Command = command;
            template.Data = data;
            using (var producer = new ProducerBuilder<string, string>(config).Build())
            {
                producer.Produce("InTopic", new Message<string, string>
                {
                    Value = JsonConvert.SerializeObject(template),
                    Key = key,
                });
                producer.Flush();
            }
        }
    }
}
