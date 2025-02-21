using System.Runtime.Serialization;
using Domain.Entities;

namespace Application.Dto.Response;

public class EditorResponseDto
{
    public long Id { get; set; }
    public string Login { get; set; }
    public string Password { get; set; }
    public string Firstname { get; set; }
    public string Lastname { get; set; }

    public List<Article> Articles { get; set; } = [];
}