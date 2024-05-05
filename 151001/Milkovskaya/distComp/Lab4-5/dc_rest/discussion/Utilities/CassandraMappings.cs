
using discussion.Models;

namespace discussion.Utilities;

public class CassandraMappings : Cassandra.Mapping.Mappings
{
    public CassandraMappings()
    {
        For<Note>()
            .TableName("tbl_note")
            .PartitionKey(x => x.Country)
            .ClusteringKey(x => x.NewsId)
            .ClusteringKey(x => x.Id)
            .Column(x => x.Content, cm => cm.WithName("content"))
            .Column(x => x.NewsId, cm => cm.WithName("newsId"))
            .Column(x => x.Country, cm => cm.WithName("country"))
            .Column(x => x.Id, cm => cm.WithName("id"));
    }
}