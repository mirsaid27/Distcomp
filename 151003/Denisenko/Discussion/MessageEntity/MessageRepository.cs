using Cassandra.Mapping;
using Discussion.Common;
using Discussion.MessageEntity.Interface;
using Discussion.Storage.Cassandra;

namespace Discussion.MessageEntity
{
    public class MessageRepository(CassandraStorage storage, Random random) : AbstractCrudRepository<Message>(storage, random), IMessageRepository
    {
        public override Message Add(Message entity)
        {
            entity.Country = "Belarus";

            return base.Add(entity);
        }

        public override async Task<Message> AddAsync(Message entity)
        {
            entity.Country = "Belarus";

            return await base.AddAsync(entity);
        }

        public override async Task<Message> UpdateAsync(Message entity)
        {
            entity.Country = "Belarus";

            return await base.UpdateAsync(entity);
        }
    }
}
