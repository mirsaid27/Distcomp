until printf "" 2>>/dev/null >>/dev/tcp/cassandra/9042; do 
    sleep 5;
    echo "Waiting for cassandra...";
done

echo "Creating keyspace and table..."
cqlsh cassandra -e "CREATE KEYSPACE IF NOT EXISTS distcomp WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};"
cqlsh cassandra -e "CREATE TABLE IF NOT EXISTS tbl_post (country text, story_id bigint, id bigint, content text, PRIMARY KEY ((country), story_id, id))"