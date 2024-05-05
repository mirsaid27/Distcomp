namespace Discussion.CommentEntity.Dto
{
    public record class CommentResponseTO(int Id, int StoryId, string Content, string Country);
}
