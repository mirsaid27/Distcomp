using dc_rest.Data;
using dc_rest.Models;
using dc_rest.Repositories.PostgresRepositories;
using EFCore.Toolkit.Testing;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using Microsoft.EntityFrameworkCore.ChangeTracking.Internal;
using Moq;

namespace Tests.DbRepositoriesTests
{
    public class CreatorRepositoryTests
    {
        private readonly Mock<AppDbContext> _dbContextMock = new();
        private readonly PostgresCreatorRepository _creatorRepository;
        
        List<Creator> creators = new List<Creator>()
        {
            new Creator()
            {
                Id = 0,
                Firstname = "name",
                Lastname = "lastname",
                Login = "login",
                Password = "password",
            },
            new Creator()
            {
                Id = 1,
                Firstname = "somename",
                Lastname = "somelastname",
                Login = "somelogin",
                Password = "somepassword",
            },
        };

        public CreatorRepositoryTests()
        {
            _creatorRepository = new PostgresCreatorRepository(_dbContextMock.Object);
            /*_dbContextMock.Setup<DbSet<Creator>>(x => x.Creators)
                .ReturnsDbSet(creators);*/
            _dbContextMock.Setup(x => x.Creators)
                .Returns(MockDbSet(creators).Object);
            //_dbContextMock.Setup(x => x.SaveChangesAsync(CancellationToken.None)).ReturnsAsync(1);
        }

        private Mock<DbSet<T>> MockDbSet<T>(List<T> data) where T : class
        {
            var queryableData = data.AsQueryable();
            var mockSet = new Mock<DbSet<T>>();
            mockSet.As<IAsyncEnumerable<T>>()
                .Setup(m => m.GetAsyncEnumerator(default))
                .Returns(new TestAsyncEnumerator<T>(queryableData.GetEnumerator()));

            mockSet.As<IQueryable<T>>()
                .Setup(m => m.Provider)
                .Returns(new TestAsyncQueryProvider<T>(queryableData.Provider));
            mockSet.As<IQueryable<T>>().Setup(m => m.Expression).Returns(queryableData.Expression);
            mockSet.As<IQueryable<T>>().Setup(m => m.ElementType).Returns(queryableData.ElementType);
            mockSet.As<IQueryable<T>>().Setup(m => m.GetEnumerator()).Returns(() => queryableData.GetEnumerator());

            return mockSet;
        }

        [Fact]
        public async Task CreateAsync_ValidEntity_ReturnsEntity()
        {
            
        }

        [Fact]
        public async Task UpdateAsync_EntityNotExists_ReturnsNull()
        {
            Creator creator = new Creator()
            {
                Id = 2,
                Firstname = "name",
                Lastname = "lastname",
                Login = "login",
                Password = "password",
            };

            var up = await _creatorRepository.UpdateAsync(creator);

            Assert.Null(up);
        }

        [Fact]
        public async Task DeleteAsync_NotValidId_ReturnsFalse()
        {
            
            var res = await _creatorRepository.DeleteAsync(10);
            
            Assert.False(res);
        }

        [Fact]
        public async Task GetByIdAsync_NotValidId_ReturnsNull()
        {
            
            var resCreator = await _creatorRepository.GetByIdAsync(10);
            
            Assert.Null(resCreator);
        }

        [Fact]
        public async Task GetByIdAsync_ValidId_ReturnsEntity()
        {
            
            var resCreator = await _creatorRepository.GetByIdAsync(0);
            
            Assert.NotNull(resCreator);
            Assert.Equal("name", resCreator.Firstname);
        }
    }
}
