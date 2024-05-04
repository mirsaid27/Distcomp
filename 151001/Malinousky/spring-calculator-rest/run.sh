#!/bin/bash
docker run --name dist-comp-postgres \ 
	-v dist-comp-postgres-data:/docker-entrypoint-initdb.d \
	-e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=distcomp \
	-dp 127.0.0.1:5432:5432 postgres:16
