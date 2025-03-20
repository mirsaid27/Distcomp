package notice

import (
	"context"

	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/model"
	"github.com/Khmelov/Distcomp/251004/Sazonov/pkg/logger"
	"go.uber.org/zap"
)

type newsRepo interface {
	GetNews(ctx context.Context, id int64) (model.News, error)
}

type syncNotice interface {
	GetNotice(ctx context.Context, id int64) (model.Notice, error)
	ListNotices(ctx context.Context) ([]model.Notice, error)
	CreateNotice(ctx context.Context, args model.Notice) (model.Notice, error)
	UpdateNotice(ctx context.Context, args model.Notice) (model.Notice, error)
	DeleteNotice(ctx context.Context, id int64) error
}

type asyncNotice interface {
	CreateNoticeAsync(ctx context.Context, args model.Notice) error
}

type noticeService struct {
	syncNotice
	asyncNotice
	news newsRepo
}

func New(syncNotice syncNotice, asyncNotice asyncNotice, news newsRepo) *noticeService {
	return &noticeService{
		syncNotice:  syncNotice,
		asyncNotice: asyncNotice,
		news:        news,
	}
}

func (n *noticeService) CreateNoticeAsync(
	ctx context.Context,
	args model.Notice,
) error {
	_, err := n.news.GetNews(ctx, args.NewsID)
	if err != nil {
		return err
	}

	err = n.asyncNotice.CreateNoticeAsync(ctx, args)
	if err != nil {
		return err
	}

	return nil
}

func (n *noticeService) CreateNotice(ctx context.Context, args model.Notice) (model.Notice, error) {
	_, err := n.news.GetNews(ctx, args.NewsID)
	if err != nil {
		return model.Notice{}, err
	}

	notice, err := n.syncNotice.CreateNotice(ctx, args)
	if err != nil {
		logger.Error(ctx, "create notice", zap.Error(err))
		return model.Notice{}, err
	}

	return notice, nil
}
