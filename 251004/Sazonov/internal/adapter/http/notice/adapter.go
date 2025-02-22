package notice

import "net/http"

type noticeAdapter struct {
	cli *http.Client

	addr string
}

func New(cli *http.Client, addr string) *noticeAdapter {
	return &noticeAdapter{
		cli:  cli,
		addr: addr,
	}
}
