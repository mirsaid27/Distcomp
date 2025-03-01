package notice

import (
	"bytes"
	"context"
	"encoding/json"
	"fmt"
	"net/http"

	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/model"
	"github.com/stackus/errors"
)

var ErrNoticeNotFound = errors.Wrap(errors.ErrNotFound, "notice is not found")

func (n *noticeAdapter) GetNotice(ctx context.Context, id int64) (model.Notice, error) {
	url := fmt.Sprintf("http://%s/api/v1.0/notices/%d", n.addr, id)

	req, err := http.NewRequest("GET", url, nil)
	if err != nil {
		return model.Notice{}, err
	}

	resp, err := n.cli.Do(req)
	if err != nil {
		return model.Notice{}, err
	}
	defer resp.Body.Close()

	var notice model.Notice

	if _err := json.NewDecoder(resp.Body).Decode(&notice); _err != nil {
		return model.Notice{}, _err
	}

	return notice, nil
}

func (n *noticeAdapter) ListNotices(ctx context.Context) ([]model.Notice, error) {
	url := fmt.Sprintf("http://%s/api/v1.0/notices", n.addr)

	req, err := http.NewRequest("GET", url, nil)
	if err != nil {
		return nil, err
	}

	resp, err := n.cli.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	var notices []model.Notice

	if _err := json.NewDecoder(resp.Body).Decode(&notices); _err != nil {
		return nil, _err
	}

	return notices, nil
}

func (n *noticeAdapter) CreateNotice(ctx context.Context, args model.Notice) (model.Notice, error) {
	url := fmt.Sprintf("http://%s/api/v1.0/notices", n.addr)

	data, err := json.Marshal(args)
	if err != nil {
		return model.Notice{}, err
	}

	body := bytes.NewBuffer(data)

	req, err := http.NewRequest("POST", url, body)
	if err != nil {
		return model.Notice{}, err
	}

	req.Header.Add("Content-Type", "application/json")

	resp, err := n.cli.Do(req)
	if err != nil {
		return model.Notice{}, err
	}
	defer resp.Body.Close()

	var notice model.Notice

	if _err := json.NewDecoder(resp.Body).Decode(&notice); _err != nil {
		return model.Notice{}, _err
	}

	return notice, nil
}

func (n *noticeAdapter) UpdateNotice(ctx context.Context, args model.Notice) (model.Notice, error) {
	url := fmt.Sprintf("http://%s/api/v1.0/notices", n.addr)

	data, err := json.Marshal(args)
	if err != nil {
		return model.Notice{}, err
	}

	body := bytes.NewBuffer(data)

	req, err := http.NewRequest("PUT", url, body)
	if err != nil {
		return model.Notice{}, err
	}

	req.Header.Add("Content-Type", "application/json")

	resp, err := n.cli.Do(req)
	if err != nil {
		return model.Notice{}, err
	}
	defer resp.Body.Close()

	var notice model.Notice

	if _err := json.NewDecoder(resp.Body).Decode(&notice); _err != nil {
		return model.Notice{}, _err
	}

	return notice, nil
}

func (n *noticeAdapter) DeleteNotice(ctx context.Context, id int64) error {
	url := fmt.Sprintf("http://%s/api/v1.0/notices/%d", n.addr, id)

	req, err := http.NewRequest("DELETE", url, nil)
	if err != nil {
		return err
	}

	resp, err := n.cli.Do(req)
	if err != nil {
		return err
	}
	defer resp.Body.Close()

	if resp.StatusCode == http.StatusNotFound {
		return ErrNoticeNotFound
	}

	return nil
}
