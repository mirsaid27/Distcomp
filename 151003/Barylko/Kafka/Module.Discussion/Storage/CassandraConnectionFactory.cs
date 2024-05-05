using Cassandra;
using Polly;
using Polly.Contrib.WaitAndRetry;
using ISession = Cassandra.ISession;
namespace Discussion.Storage;

public sealed class CassandraConnectionFactory(CassandraDbOptions options, ILogger<CassandraConnectionFactory> logger)
{
    private readonly Cluster _cluster = Cluster.Builder()
        .AddContactPoint(options.Host)
        .WithPort(options.Port)
        .WithDefaultKeyspace(options.Keyspace)
        .WithReconnectionPolicy(new ConstantReconnectionPolicy(10000))
        .Build();

    private readonly Policy _retryPolicy = Policy
        .Handle<NoHostAvailableException>()
        .WaitAndRetry(Backoff.DecorrelatedJitterBackoffV2(TimeSpan.FromSeconds(3), 5));

    public ISession Connect() => _retryPolicy.Execute(() =>
    {
        ISession session = _cluster.Connect();
        logger.LogInformation("Connected to Cassandra cluster: {ClusterName}", _cluster.Metadata.ClusterName);
        return session;
    });
}
