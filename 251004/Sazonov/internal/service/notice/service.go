package notice

import (
	"context"

	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/adapter"
	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/model"
)

type newsRepo interface {
	GetNews(ctx context.Context, id int64) (model.News, error)
}

type noticeService struct {
	adapter.NoticeAdapter
	news newsRepo
}

func New(notice adapter.NoticeAdapter, news newsRepo) *noticeService {
	return &noticeService{
		NoticeAdapter: notice,
		news:          news,
	}
}

func (n *noticeService) CreateNotice(ctx context.Context, args model.Notice) (model.Notice, error) {
	_, err := n.news.GetNews(ctx, args.NewsID)
	if err != nil {
		return model.Notice{}, err
	}

	notice, err := n.NoticeAdapter.CreateNotice(ctx, args)
	if err != nil {
		return model.Notice{}, err
	}

	return notice, nil
}
