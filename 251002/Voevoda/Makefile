.PHONY: run
run:
	go run cmd/distcomp/main.go

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
