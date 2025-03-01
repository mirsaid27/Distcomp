package adapter

import (
	"context"

	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/model"
)

type Adapter interface {
	NoticeAdapter
}

type NoticeAdapter interface {
	GetNotice(ctx context.Context, id int64) (model.Notice, error)
	ListNotices(ctx context.Context) ([]model.Notice, error)
	CreateNotice(ctx context.Context, args model.Notice) (model.Notice, error)
	UpdateNotice(ctx context.Context, args model.Notice) (model.Notice, error)
	DeleteNotice(ctx context.Context, id int64) error
}
