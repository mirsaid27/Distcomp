using Cassandra;
using Cassandra.Mapping;
using Discussion.Models;

namespace Discussion.Repositories
{
    public class CommentRepository : IRepository<Comment>
    {
        private readonly Cluster _cluster;
        private readonly Cassandra.ISession _session;

        public CommentRepository()
        {
            _cluster = Cluster.Builder().AddContactPoint("cassandra").Build();
            try
            {
                _session = _cluster.Connect("distcomp");
            }
            catch (InvalidQueryException)
            {
                using (var session = _cluster.Connect())
                {
                    session.Execute("CREATE KEYSPACE IF NOT EXISTS distcomp WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}");
                    session.Execute("CREATE TABLE IF NOT EXISTS distcomp.tbl_comment (country text,tweetId int,id int,content text,PRIMARY KEY ((country), tweetId, id))");
                }
            }
            _session = _cluster.Connect("distcomp");
        }

        public void Create(Comment entity)
        {
            var mapper = new Mapper(_session);
            mapper.Insert(entity);
        }

        public Comment Get(int id)
        {
            var mapper = new Mapper(_session);
            return mapper.FirstOrDefault<Comment>($"WHERE id = {id} ALLOW FILTERING");
        }

        public void Update(Comment entity)
        {
            var n = Get(entity.id);
            entity.country = n.country;
            var mapper = new Mapper(_session);
            mapper.Update(entity);
        }

        public int Delete(int id)
        {
            var n = Get(id);
            var mapper = new Mapper(_session);
            if (n != null)
            {
                mapper.Delete<Comment>(n);
                return 1;
            }
            return 0;
        }

        public List<Comment> GetAll()
        {
            var mapper = new Mapper(_session);
            return mapper.Fetch<Comment>().ToList();
        }
    }
}
