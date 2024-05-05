namespace Publisher.Entity.DTO.ResponseTO
{
    public record class NewsResponseTO(int Id, int AuthorId, string Title, string Content, DateTime Created,
        DateTime Modified);
}
