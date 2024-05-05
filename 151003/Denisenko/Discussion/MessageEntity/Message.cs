using Discussion.Common;

namespace Discussion.MessageEntity
{
    public class Message(string content, string country) : AbstractEntity
    {
        public string Country { get; set; } = country;
        public int IssueId { get; set; }
        public string Content { get; set; } = content;

        public Message() : this(string.Empty, string.Empty) { }

        public Message(int id, int issueId, string content) : this(content, string.Empty)
        {
            Id = id;
            IssueId = issueId;
        }

        public Message(int id, int issueId, string content, string country) : this(content, country)
        {
            Id = id;
            IssueId = issueId;
        }
    }
}
