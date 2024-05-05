namespace dc_rest.Utilities;

public class SD
{
    public enum ApiType
    {
        GET,
        POST,
        PUT,
        DELETE
    }

    public static string NoteAPIBase { get; set; } = "http://localhost:24130";
}