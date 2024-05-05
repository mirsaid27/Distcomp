using dc_rest.Utilities;

namespace dc_rest.DTOs.RequestDTO;

public class RequestDto
{
    public SD.ApiType ApiType { get; set; } = SD.ApiType.GET;
    public string Url { get; set; }
    public object Data { get; set; }
    public string AcceptLanguage { get; set; }
}