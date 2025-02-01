package notice

import (
	"context"
	"database/sql"
	stderrors "errors"

	"github.com/Khmelov/Distcomp/251004/Sazonov/internal/model"
	"github.com/jackc/pgerrcode"
	"github.com/jackc/pgx/v5/pgconn"
	"github.com/stackus/errors"
)

var (
	ErrNoticeNotFound = errors.Wrap(errors.ErrNotFound, "notice is not found")

	ErrNoticeAlreadyExists = errors.Wrap(errors.ErrAlreadyExists, "notice already exists")
)

func (n *NoticeRepo) GetNotice(ctx context.Context, id int64) (model.Notice, error) {
	const query = `SELECT * FROM Notice WHERE id = $1 LIMIT 1`

	var notice model.Notice
	if err := n.db.GetContext(ctx, &notice, query, id); err != nil {
		if stderrors.Is(err, sql.ErrNoRows) {
			return model.Notice{}, ErrNoticeNotFound
		}

		return model.Notice{}, err
	}

	return notice, nil
}

func (n *NoticeRepo) ListNotices(ctx context.Context) ([]model.Notice, error) {
	const query = `SELECT * FROM Notice`

	notices := []model.Notice{}
	if err := n.db.SelectContext(ctx, &notices, query); err != nil {
		return nil, err
	}

	return notices, nil
}

func (n *NoticeRepo) CreateNotice(ctx context.Context, args model.Notice) (model.Notice, error) {
	const query = `INSERT INTO Notice (
		newsId, content
	) VALUES (
		:newsId, :content
	) RETURNING *`

	rows, err := n.db.NamedQueryContext(ctx, query, args)
	if err != nil {
		var pgErr *pgconn.PgError

		if stderrors.As(err, &pgErr) && pgErr.Code == pgerrcode.UniqueViolation {
			return model.Notice{}, ErrNoticeAlreadyExists
		}

		return model.Notice{}, err
	}

	var notice model.Notice

	for rows.Next() {
		rows.StructScan(notice)
	}

	if err := rows.Err(); err != nil {
		return model.Notice{}, err
	}

	return model.Notice{}, nil
}

func (n *NoticeRepo) UpdateNotice(ctx context.Context, args model.Notice) error {
	const query = `UPDATE Notice SET
		newsId = COALESCE(NULLIF(:newsId, 0), newsId),
		content = COALESCE(NULLIF(:content, ''), content)	
	WHERE id = :id`

	result, err := n.db.NamedExecContext(ctx, query, args)
	if err != nil {
		var pgErr *pgconn.PgError

		if stderrors.As(err, &pgErr) && pgErr.Code == pgerrcode.UniqueViolation {
			return ErrNoticeAlreadyExists
		}

		return err
	}

	rowsAffected, err := result.RowsAffected()
	if err != nil {
		return err
	}

	if rowsAffected != 1 {
		return ErrNoticeNotFound
	}

	return nil
}

func (n *NoticeRepo) DeleteNotice(ctx context.Context, id int64) error {
	const query = `DELETE FROM Notice WHERE id = $1`

	result, err := n.db.ExecContext(ctx, query, id)
	if err != nil {
		return err
	}

	rowsAffected, err := result.RowsAffected()
	if err != nil {
		return err
	}

	if rowsAffected != 1 {
		return ErrNoticeNotFound
	}

	return nil
}
