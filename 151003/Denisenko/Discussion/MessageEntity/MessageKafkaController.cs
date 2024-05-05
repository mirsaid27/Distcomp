using Confluent.Kafka;
using Discussion.Kafka;
using Discussion.MessageEntity.Dto;
using Discussion.MessageEntity.Interface;
using Discussion.MessageEntity.Kafka;
using Newtonsoft.Json;

namespace Discussion.MessageEntity
{
    public class MessageKafkaController(IProducer<string, string> producer, IMessageService service)
    {
        private static readonly IConsumer<string, string> _consumer;
        private bool isConsuming = true;

        static MessageKafkaController()
        {
            var consumerConfig = new ConsumerConfig
            {
                BootstrapServers = "kafka:9092",
                GroupId = "discussion",
                AutoOffsetReset = AutoOffsetReset.Earliest,
                EnableAutoCommit = true,
                EnableAutoOffsetStore = true
            };
            _consumer = new ConsumerBuilder<string, string>(consumerConfig).Build();
            _consumer.Subscribe("InTopic");

            var producerConfig = new ProducerConfig
            {
                BootstrapServers = "kafka:9092"
            };
        }

        public async Task StartConsuming()
        {
            while (isConsuming)
            {
                var res = _consumer.Consume();
                var key = res.Message.Key;
                var value = res.Message.Value;
                var entity = JsonConvert.DeserializeObject<MessageKafkaRequest>(value);

                if (entity is null)
                {
                    continue;
                }

                var requestTo = entity.RequestTO;
                switch (entity.Method)
                {
                    case HttpMethod m when m == HttpMethod.Get && entity.RequestTO.Id == default:
                        value = await GetAll(requestTo);
                        break;
                    case HttpMethod m when m == HttpMethod.Get:
                        value = await GetById(requestTo);
                        break;
                    case HttpMethod m when m == HttpMethod.Post:
                        value = await Create(requestTo);
                        break;
                    case HttpMethod m when m == HttpMethod.Put:
                        value = await Update(requestTo);
                        break;
                    case HttpMethod m when m == HttpMethod.Delete:
                        value = await Delete(requestTo);
                        break;
                    default:
                        break;
                }

                await producer.ProduceAsync("OutTopic", new Message<string, string>
                {
                    Key = key,
                    Value = value
                });
                producer.Flush(TimeSpan.FromSeconds(1));
            }
        }

        public void StopConsuming()
        {
            isConsuming = false;
        }

        private async Task<string> GetAll(MessageRequestTO request)
        {
            string response;
            var emptyResponse = new MessageKafkaResponse(
                new MessageResponseTO(
                    default, default, string.Empty, string.Empty),
                State.Decline);
            List<MessageKafkaResponse>? kafkaResponseTo = [emptyResponse];

            try
            {
                var messageResponseList = await service.GetAll();
                kafkaResponseTo = messageResponseList.Select(r => new MessageKafkaResponse(r, State.Approve)).ToList();
            }
            finally
            {
                response = JsonConvert.SerializeObject(kafkaResponseTo);
            }

            return response;
        }

        private async Task<string> GetById(MessageRequestTO request)
        {
            string response;
            var emptyResponse = new MessageKafkaResponse(
                new MessageResponseTO(
                    default, default, string.Empty, string.Empty),
                State.Decline);
            MessageKafkaResponse messageKafkaResponse = emptyResponse;

            try
            {
                var messageResponseTO = await service.GetByID(request.Id);
                messageKafkaResponse = new MessageKafkaResponse(messageResponseTO, State.Approve);
            }
            catch { }
            finally
            {
                response = JsonConvert.SerializeObject(messageKafkaResponse);
            }

            return response;
        }

        private async Task<string> Create(MessageRequestTO request)
        {
            string response;
            var emptyResponse = new MessageKafkaResponse(
                new MessageResponseTO(
                    default, default, string.Empty, string.Empty),
                State.Decline);
            MessageKafkaResponse messageKafkaResponse = emptyResponse;

            try
            {
                var messageResponseTO = await service.Add(request);
                messageKafkaResponse = new MessageKafkaResponse(messageResponseTO, State.Approve);
            }
            finally
            {
                response = JsonConvert.SerializeObject(messageKafkaResponse);
            }

            return response;
        }

        private async Task<string> Update(MessageRequestTO request)
        {
            string response;
            var emptyResponse = new MessageKafkaResponse(
                new MessageResponseTO(
                    default, default, string.Empty, string.Empty),
                State.Decline);
            MessageKafkaResponse messageKafkaResponse = emptyResponse;

            try
            {
                var messageResponseTO = await service.Update(request);
                messageKafkaResponse = new MessageKafkaResponse(messageResponseTO, State.Approve);
            }
            finally
            {
                response = JsonConvert.SerializeObject(messageKafkaResponse);
            }

            return response;
        }

        private async Task<string> Delete(MessageRequestTO request)
        {
            string response;
            var emptyResponse = new MessageKafkaResponse(
                new MessageResponseTO(
                    default, default, string.Empty, string.Empty),
                State.Decline);
            MessageKafkaResponse messageKafkaResponse = emptyResponse;

            try
            {
                var messageResponseTO = await service.Remove(request.Id);
                messageKafkaResponse = new MessageKafkaResponse(
                    new MessageResponseTO(request.Id, default, string.Empty, string.Empty), State.Approve);
            }
            finally
            {
                response = JsonConvert.SerializeObject(messageKafkaResponse);
            }

            return response;
        }
    }
}
