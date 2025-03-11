namespace DistComp.Models;

public class Tag : BaseModel
{
    public string Name { get; set; }

    public List<Story> Stories { get; set; } = [];
}