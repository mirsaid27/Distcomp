package main

import (
	"log/slog"
	"net/http"

	"github.com/go-chi/chi/v5"
	handler "github.com/strcarne/task310-rest/internal/handler/httpapi"
)

const serveAddress = "0.0.0.0:24110"

func main() {
	router := chi.NewRouter()

	handler.RegisterV1(router)

	slog.Info("starting server", slog.String("address", serveAddress))
	if err := http.ListenAndServe(serveAddress, router); err != nil {
		slog.Error("failed to start listening", slog.String("address", serveAddress))
	}
}
