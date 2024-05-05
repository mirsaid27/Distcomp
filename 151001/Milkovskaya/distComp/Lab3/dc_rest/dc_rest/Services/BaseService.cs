using System.Net;
using System.Text;
using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;
using dc_rest.Exceptions;
using dc_rest.Services.Interfaces;
using dc_rest.Utilities;
using Newtonsoft.Json;

namespace dc_rest.Services;

public class BaseService  : IBaseService
{
     private readonly IHttpClientFactory _httpClientFactory;
    public BaseService(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }
    public async Task<string> SendAsync(RequestDto requestDto)
    {
        try
        {
            HttpClient client = _httpClientFactory.CreateClient("distcompAPI");
            HttpRequestMessage message = new HttpRequestMessage();
            
            message.Headers.Add("Accept", "application/json");
            
            message.RequestUri = new Uri(requestDto.Url);
            
            if (requestDto.Data != null)
            {
                message.Content = new StringContent(JsonConvert.SerializeObject(requestDto.Data), Encoding.UTF8, "application/json");
            }

            HttpResponseMessage? apiResponse = null;
            switch (requestDto.ApiType)
            {
                case SD.ApiType.POST:
                {
                    message.Method = HttpMethod.Post;
                    break;
                }
                case SD.ApiType.PUT:
                {
                    message.Method = HttpMethod.Put;
                    break;
                }
                case SD.ApiType.DELETE:
                {
                    message.Method = HttpMethod.Delete;
                    break;
                }
                default:
                {
                    message.Method = HttpMethod.Get;
                    break;
                }
            }

            apiResponse = await client.SendAsync(message);
            
            switch (apiResponse.StatusCode)
            {
                case HttpStatusCode.NotFound:
                {
                    throw new NotFoundException();
                }
                case HttpStatusCode.BadRequest:
                {
                    break;
                }
                case HttpStatusCode.InternalServerError:
                {
                    break;
                }
                default:
                {
                    var apiContent = await apiResponse.Content.ReadAsStringAsync();
                    return apiContent;
                }
            }

            return null;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}