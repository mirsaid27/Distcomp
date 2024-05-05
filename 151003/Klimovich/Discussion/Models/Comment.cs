using Cassandra.Mapping.Attributes;

namespace Discussion.Models
{
    [Table("tbl_Comment")]
    public class Comment
    {
        [ClusteringKey]
        public int id { get; set; }

        [ClusteringKey]
        public int tweetId { get; set; }
        public string content { get; set; }
        [PartitionKey]
        public string country { get; set; }
    }
}
