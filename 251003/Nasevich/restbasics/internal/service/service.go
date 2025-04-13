package service

import (
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/service/creator"
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/service/issue"
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/service/mark"
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/service/message"
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/storage"
)

type Service struct {
	db storage.Storage

	Creator CreatorService
	Issue   IssueService
	Message MessageService
	Mark    MarkService
}

func New(db storage.Storage) Service {
	return Service{
		db: db,

		Creator: creator.New(db.DB.CreatorInst),
		Issue:   issue.New(db.DB.IssueInst),
		Message: message.New(db.DB.MessageInst),
		Mark:    mark.New(db.DB.MarkInst),
	}
}
