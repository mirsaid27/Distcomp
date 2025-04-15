package storage

import (
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/publisher/internal/storage/psql"
)

type Storage struct {
	DB *psql.PSQL
}

func New() (*Storage, error) {
	db, err := psql.New()
	if err != nil {
		return nil, err
	}

	return &Storage{
		DB: db,
	}, nil
}
