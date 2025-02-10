using System.Runtime.Serialization;
using System.Text.Json.Serialization;

namespace Service.DTO.Response.Mark;

[Serializable]
[DataContract(Name="mark")]
public class MarkResponseToGetById
{
    public long Id { get; init; }
    public string Name { get; init; }
}