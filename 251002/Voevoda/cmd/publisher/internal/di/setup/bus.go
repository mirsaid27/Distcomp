package setup

import (
	"log/slog"

	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/config"
	"github.com/strcarne/distributed-calculations/internal/bus"
)

func mustBus(cfg config.Config, logger *slog.Logger) *bus.Service {
	bus := bus.NewService(cfg.Kafka.Brokers, cfg.Kafka.InTopic, cfg.Kafka.OutTopic, logger)

	return bus
}
