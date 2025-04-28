package di

import (
	"log/slog"

	"github.com/gocql/gocql"
	"github.com/strcarne/distributed-calculations/cmd/discussion/internal/config"
	"github.com/strcarne/distributed-calculations/internal/bus"
)

type Container struct {
	Config   config.Config
	Bus      *bus.Service
	Session  *gocql.Session
	Logger   *slog.Logger
	Services Services
}
