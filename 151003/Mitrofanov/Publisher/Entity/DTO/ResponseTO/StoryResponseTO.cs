namespace Publisher.Entity.DTO.ResponseTO
{
    public record class StoryResponseTO(int Id, int EditorId, string Title, string Content, DateTime Created,
        DateTime Modified);
}
