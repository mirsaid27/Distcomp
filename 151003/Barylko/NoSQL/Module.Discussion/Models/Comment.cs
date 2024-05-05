using Cassandra.Mapping.Attributes;
namespace Discussion.Models;

[Table("tbl_comment", AllowFiltering = true)]
public class Comment
{
    [PartitionKey] public string Country { get; set; } = string.Empty;

    [ClusteringKey(0)] public long IssueId { get; set; }
    
    [ClusteringKey(1)] public long Id { get; set; }
    
    public string Content { get; set; } = string.Empty;
}
