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

type repository struct {
	db *sqlx.DB

	WriterRepo
	NewsRepo
	NoticeRepo
	LabelRepo
}

func New(cfg config.StorageConfig) (Repository, error) {
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

	repo := &repository{
		db: db,

		WriterRepo: writer.New(db),
		NewsRepo:   news.New(db),
		NoticeRepo: notice.New(db),
		LabelRepo:  label.New(db),
	}

	return repo, nil
}

func (r *repository) Close() {
	if r.db != nil {
		r.db.Close()
	}
}
