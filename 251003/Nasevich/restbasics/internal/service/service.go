package service

import (
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/service/creator"
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/storage"
)

type Service struct {
	db storage.Storage

	Creator CreatorService
}

func New(db storage.Storage) Service {
	return Service{
		db: db,

		Creator: creator.New(db.DB.CreatorInst),
	}
}
