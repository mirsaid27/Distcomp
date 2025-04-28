package setup

import (
	"context"
	"log/slog"
	"os"

	"github.com/jackc/pgx/v5"
	"github.com/strcarne/task310-rest/internal/repository/psql/generated"
)

func MustQueries() *generated.Queries {
	dsn := os.Getenv("DATABASE_URL")
	slog.Info("connecting to database", slog.String("dsn", dsn))

	db, err := pgx.Connect(context.Background(), dsn)
	if err != nil {
		panic(err)
	}

	queries := generated.New(db)
	if queries == nil {
		panic("failed to create sqlc queries")
	}

	return queries
}
