using Discussion.Common;

namespace Discussion.CommentEntity
{
    public class Comment(string content, string country) : AbstractEntity
    {
        public string Country { get; set; } = country;
        public int StoryId { get; set; }
        public string Content { get; set; } = content;

        public Comment() : this(string.Empty, string.Empty) { }

        public Comment(int id, int storyId, string content) : this(content, string.Empty)
        {
            Id = id;
            StoryId = storyId;
        }

        public Comment(int id, int storyId, string content, string country) : this(content, country)
        {
            Id = id;
            StoryId = storyId;
        }
    }
}
