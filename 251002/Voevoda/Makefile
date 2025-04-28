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
