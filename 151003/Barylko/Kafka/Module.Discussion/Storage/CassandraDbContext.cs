using Cassandra.Data.Linq;
using Discussion.Models;
namespace Discussion.Storage;

public sealed class CassandraDbContext(CassandraConnectionFactory connectionFactory) : IDisposable
{
    public Table<Comment> Comments { get; set; } = new(connectionFactory.Connect());
    
    public void Dispose() => Comments.GetSession().Dispose();
}
