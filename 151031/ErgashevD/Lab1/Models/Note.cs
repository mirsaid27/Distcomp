namespace Lab1.Models
{
    public class Note : BaseModel
    {
        public long NewsId { get; set; }

        public News News { get; set; }

        public string Content { get; set; }
    }
}
