package repository

import (
	"context"

	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/config"
	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/repository/psql/label"
	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/repository/psql/news"
	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/repository/psql/notice"
	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/repository/psql/writer"
	"github.com/Khmelov/Distcomp/251004/Sazonov/pkg/postgres"
	"github.com/jmoiron/sqlx"
)

const (
	MemoryStorageType string = "memory"

	PostgresStorageType string = "postgres"
)

type Repository struct {
	db *sqlx.DB

	WriterRepo
	NewsRepo
	NoticeRepo
	LabelRepo
}

func New(cfg config.StorageConfig) (*Repository, error) {
	var repo Repository

	switch cfg.Type {
	case MemoryStorageType:
	case PostgresStorageType:
		db, err := postgres.Connect(
			context.Background(),
			postgres.Config{
				User:     cfg.User,
				Password: cfg.Password,
				Host:     cfg.Host,
				Port:     cfg.Port,
				DBName:   cfg.DBName,
				SSLMode:  cfg.SSLMode,
			},
		)
		if err != nil {
			return nil, err
		}

		repo = Repository{
			db: db,

			WriterRepo: writer.New(db),
			NewsRepo:   news.New(db),
			NoticeRepo: notice.New(db),
			LabelRepo:  label.New(db),
		}
	}

	return &repo, nil
}

func (r *Repository) Close() {
	if r.db != nil {
		r.db.Close()
	}
}
