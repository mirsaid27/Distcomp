package main

import (
	"log/slog"
	"net/http"

	"github.com/go-chi/chi/v5"
	"github.com/joho/godotenv"

	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/handler/httpapi"
	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/setup"
)

const serveAddress = "0.0.0.0:24110"

func main() {
	if err := godotenv.Load(); err != nil {
		slog.Error("failed to load .env file", slog.String("error", err.Error()))

		return
	}

	router := chi.NewRouter()
	queries := setup.MustQueries()

	httpapi.RegisterV1(router, queries)

	slog.Info("starting server", slog.String("address", serveAddress))
	if err := http.ListenAndServe(serveAddress, router); err != nil {
		slog.Error("failed to start listening", slog.String("address", serveAddress))
	}
}
