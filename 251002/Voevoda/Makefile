.PHONY: run-publisher
run-publisher:
	go run cmd/publisher/main.go

.PHONY: run-discussion
run-discussion:
	go run cmd/discussion/main.go

.PHONY: fmt
fmt:
	golines -w .
	gofumpt -w .

.PHONY: lint
lint:
	golangci-lint run

.PHONY: run-psql
run-psql:
	docker run --name psql -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=distcomp -d -p 5432:5432 postgres

.PHONY: run-cassandra
run-cassandra:
	docker run --name cassandra-instance -p 9042:9042 -d cassandra:latest
	@echo "Waiting for Cassandra to start..."
	@sleep 60
	@echo "Cassandra should be ready now."
	docker exec -it cassandra-instance cqlsh -e "CREATE KEYSPACE IF NOT EXISTS distcomp WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };"
