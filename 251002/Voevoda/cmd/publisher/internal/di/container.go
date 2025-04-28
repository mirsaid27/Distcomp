package di

import (
	"log/slog"

	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/config"
	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/repository/psql/generated"
	"github.com/strcarne/distributed-calculations/internal/bus"
)

type Container struct {
	Config   config.Config
	Bus      *bus.Service
	Queries  *generated.Queries
	Logger   *slog.Logger
	Services Services
}
