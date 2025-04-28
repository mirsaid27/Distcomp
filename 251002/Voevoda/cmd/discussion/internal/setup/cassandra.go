package setup

import "github.com/gocql/gocql"

func MustCassandra() *gocql.Session {
	cluster := gocql.NewCluster("localhost:9042")

	cluster.Keyspace = "distcomp"
	cluster.Consistency = gocql.Quorum

	session, err := cluster.CreateSession()
	if err != nil {
		panic(err)
	}

	return session
}
