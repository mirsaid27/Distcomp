using Service.DTO.Request.Editor;
using Service.DTO.Request.Article;
using Service.DTO.Request.Post;
using Service.DTO.Response.Editor;
using Service.DTO.Response.Article;
using Service.DTO.Response.Post;
using Service.Interfaces;
using Microsoft.AspNetCore.Mvc;
using Moq;
using Presentation.Controllers;

namespace Tests.UnitTests;

public class ArticleControllerTests
{
    private readonly Mock<IArticleService> _mockArticleService;
    private readonly Mock<IEditorService> _mockEditorService;
    private readonly Mock<IPostService> _mockPostService;
    private readonly ArticleController _controller;

    public ArticleControllerTests()
    {
        _mockArticleService = new Mock<IArticleService>();
        _mockEditorService = new Mock<IEditorService>();
        _mockPostService = new Mock<IPostService>();
        _controller = new ArticleController(_mockArticleService.Object, 
            _mockEditorService.Object, _mockPostService.Object);
    }

    [Fact]
    public async Task GetAllArticles_Should_Return_OkResult_With_Articles()
    {
        #region Arrange

        var articleList = new List<ArticleResponseToGetById>
        {
            new ArticleResponseToGetById { Id = 1, Title = "First Article" },
            new ArticleResponseToGetById { Id = 2, Title = "Second Article" }
        };

        _mockArticleService.Setup(service => service.GetArticle(It.IsAny<ArticleRequestToGetAll>()))
            .ReturnsAsync(articleList);

        #endregion

        #region Act

        var result = await _controller.GetAllArticles();

        #endregion

        #region Assert

        var okResult = Assert.IsType<OkObjectResult>(result);
        var returnValue = Assert.IsAssignableFrom<IEnumerable<ArticleResponseToGetById>>(okResult.Value);
        Assert.Equal(2, returnValue.Count());

        #endregion
    }

    [Fact]
    public async Task GetEditorByArticleId_Should_Return_OkResult_With_Editor()
    {
        #region Arrange

        var editor = new EditorResponseToGetById { Id = 1, Login = "damn", Lastname = "42", Firstname = "Whatever" };
        _mockEditorService.Setup(service => service.GetEditorByArticleId(It.IsAny<EditorRequestToGetByArticleId>()))
            .ReturnsAsync(editor);

        #endregion

        #region Act

        var result = await _controller.GetEditorByArticleId(1);

        #endregion

        #region Assert

        var okResult = Assert.IsType<OkObjectResult>(result);
        var returnValue = Assert.IsType<EditorResponseToGetById>(okResult.Value);
        Assert.Equal(editor.Id, returnValue.Id);

        #endregion
    }

    [Fact]
    public async Task GetPostsByArticleId_Should_Return_OkResult_With_Posts()
    {
        #region Arrange

        var posts = new List<PostResponseToGetById>
        {
            new PostResponseToGetById { Id = 1, Content = "First Post" },
            new PostResponseToGetById { Id = 2, Content = "Second Post" }
        };

        _mockPostService.Setup(service => service.GetPostsByArticleId(It.IsAny<PostRequestToGetByArticleId>()))
            .ReturnsAsync(posts);

        #endregion

        #region Act

        var result = await _controller.GetPostsByArticleId(1);

        #endregion

        #region Assert

        var okResult = Assert.IsType<OkObjectResult>(result);
        var returnValue = Assert.IsAssignableFrom<IEnumerable<PostResponseToGetById>>(okResult.Value);
        Assert.Equal(2, returnValue.Count());

        #endregion
    }

    [Fact]
    public async Task GetArticleById_Should_Return_OkResult_With_Article()
    {
        #region Arrange

        var article = new ArticleResponseToGetById { Id = 1, Title = "First Article" };
        _mockArticleService.Setup(service => service.GetArticleById(It.IsAny<ArticleRequestToGetById>()))
            .ReturnsAsync(article);

        #endregion

        #region Act

        var result = await _controller.GetArticleById(1);

        #endregion

        #region Assert

        var okResult = Assert.IsType<OkObjectResult>(result);
        var returnValue = Assert.IsType<ArticleResponseToGetById>(okResult.Value);
        Assert.Equal(article.Id, returnValue.Id);

        #endregion
    }

    [Fact]
    public async Task CreateArticle_Should_Return_CreatedResult()
    {
        #region Arrange

        var articleRequest = new ArticleRequestToCreate { Title = "New Article" };
        var createdArticle = new ArticleResponseToGetById { Id = 1, Title = "New Article" };

        _mockArticleService.Setup(service => service.CreateArticle(articleRequest))
            .ReturnsAsync(createdArticle);

        #endregion

        #region Act

        var result = await _controller.CreateArticle(articleRequest);

        #endregion

        #region Assert

        var createdResult = Assert.IsType<CreatedAtActionResult>(result);
        var returnValue = Assert.IsType<ArticleResponseToGetById>(createdResult.Value);
        Assert.Equal(createdArticle.Id, returnValue.Id);

        #endregion
    }

    [Fact]
    public async Task UpdateArticle_Should_Return_OkResult_With_Updated_Article()
    {
        #region Arrange

        var articleModel = new ArticleRequestToFullUpdate { Id = 1, Title = "Updated Article" };
        var updatedArticle = new ArticleResponseToGetById { Id = 1, Title = "Updated Article" };
        
        _mockArticleService.Setup(service => service.UpdateArticle(articleModel))
            .ReturnsAsync(updatedArticle);

        #endregion

        #region Act

        var result = await _controller.UpdateArticle(articleModel);

        #endregion

        #region Assert

        var okResult = Assert.IsType<OkObjectResult>(result);
        var returnValue = Assert.IsType<ArticleResponseToGetById>(okResult.Value);
        Assert.Equal(updatedArticle.Title, returnValue.Title);

        #endregion
    }

    [Fact]
    public async Task DeleteArtice_Should_Return_NoContent()
    {
        #region Arrange

        _mockArticleService.Setup(service => service.DeleteArticle(It.IsAny<ArticleRequestToDeleteById>()))
            .ReturnsAsync(new ArticleResponseToGetById { Id = 1 });

        #endregion

        #region MyRegion

        var result = await _controller.DeleteArticle(1);

        #endregion Act

        #region MyRegion

        Assert.IsType<NoContentResult>(result);

        #endregion Assert
    }
}