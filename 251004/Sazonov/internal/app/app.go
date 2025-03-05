package app

import (
	"context"

	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/adapter"
	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/config"
	handler "github.com/Khmelov/Distcomp/251004/Sazonov/internal/handler/http"
	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/repository"
	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/service"
	appbase "github.com/Khmelov/Distcomp/251004/Sazonov/pkg/app"
	httpserver "github.com/Khmelov/Distcomp/251004/Sazonov/pkg/http"
)

type app struct {
	*appbase.App

	cfg config.Config
}

func New(cfg config.Config) (*app, error) {
	repository, err := repository.New(cfg.Storage)
	if err != nil {
		return nil, err
	}

	adapter := adapter.New(cfg.API.NoticeServiceAddr)

	service := service.New(repository, adapter)

	httpServer := httpserver.NewServer(httpserver.Config{
		Host:        cfg.HTTP.Host,
		Port:        cfg.HTTP.Port,
		Timeout:     cfg.HTTP.Timeout,
		IdleTimeout: cfg.HTTP.IdleTimeout,
	}, handler.New(service))

	app := &app{
		App: appbase.New(
			[]appbase.Service{
				httpServer,
			},
			[]appbase.CleanupFunc{
				func() { repository.Close() },
			},
		),

		cfg: cfg,
	}

	return app, nil
}

func (a *app) Shutdown(ctx context.Context) error {
	return a.App.Shutdown(ctx)
}
