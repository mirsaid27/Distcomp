package app

import (
	"context"
	"fmt"
	"log"
	"os/signal"
	"syscall"

	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/discussion/internal/server"
)

type app struct {
}

func New() *app {
	return &app{}
}

func (a app) Start(ctx context.Context) error {
	ctx, stop := signal.NotifyContext(ctx, syscall.SIGINT, syscall.SIGTERM)
	defer stop()

	srv := server.New()

	if err := srv.Serve(ctx); err != nil {
		return fmt.Errorf("server stopped with error: %v", err)
	}

	log.Println("server stopped")

	return nil
}
