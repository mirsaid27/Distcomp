package adapter

import (
	"net/http"

	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/adapter/http/notice"
)

type adapter struct {
	NoticeAdapter
}

func New(noticeAddr string) Adapter {
	cli := &http.Client{}

	return &adapter{
		NoticeAdapter: notice.New(cli, noticeAddr),
	}
}
