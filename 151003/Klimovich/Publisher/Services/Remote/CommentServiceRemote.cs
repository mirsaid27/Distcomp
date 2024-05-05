using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;
using Publisher.Models.DTOs.DTO;
using Publisher.Models.DTOs.ResponceTo;
using Publisher.Services.Interfaces;
using RestSharp;


namespace Publisher.Services.Remote
{
    public class CommentServiceRemote : ICommentService
    {
        private readonly string _url = "http://discussion:8080/api/v1.0/";
        public CommentResponceTo CreateComment(CommentRequestTo item)
        {
            if (item.content.Length < 2)
            {
                throw new DbUpdateException();
            }
            var client = new RestClient(_url);
            var request = new RestRequest("comments", Method.Post);
            request.AddBody(item, ContentType.Json);
            var response = client.Execute(request);
            var res = JsonConvert.DeserializeObject<CommentResponceTo>(response.Content);
            return res;
        }

        public int DeleteComment(int id)
        {
            var client = new RestClient(_url);
            var request = new RestRequest($"comments/{id}", Method.Delete);
            var response = client.Execute(request);
            if (response.StatusCode == System.Net.HttpStatusCode.NoContent)
                return 1;
            else
                return 0;
        }

        public CommentResponceTo GetComment(int id)
        {
            var client = new RestClient(_url);
            var request = new RestRequest($"comments/{id}", Method.Get);
            var response = client.Execute(request);
            var res = JsonConvert.DeserializeObject<CommentResponceTo>(response.Content);
            return res;
        }

        public List<CommentResponceTo> GetComments()
        {
            var client = new RestClient(_url);
            var request = new RestRequest("comments", Method.Get);
            var response = client.Execute(request);
            var res = JsonConvert.DeserializeObject<List<CommentResponceTo>>(response.Content);
            return res;
        }

        public CommentResponceTo UpdateComment(CommentRequestTo item)
        {
            var client = new RestClient(_url);
            var request = new RestRequest("comments", Method.Put);
            request.AddBody(item, ContentType.Json);
            var response = client.Execute(request);
            var res = JsonConvert.DeserializeObject<CommentResponceTo>(response.Content);
            return res;
        }
    }
}
