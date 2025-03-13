package label

import (
	"context"
	"database/sql"
	"errors"
	"fmt"
	"log"

	"github.com/Khmelov/Distcomp/251003/truhan/lab1/internal/model"
	"github.com/jmoiron/sqlx"
	"github.com/lib/pq"
)

var (
	ErrCommentNotFound  = fmt.Errorf("comment not found")
	ErrConstraintsCheck = fmt.Errorf("invalid data passed")
)

type repo struct {
	db *sqlx.DB
}

type Repo interface {
	Create(ctx context.Context, req model.Label) (model.Label, error)
	GetList(ctx context.Context) ([]model.Label, error)
	Get(ctx context.Context, id int64) (model.Label, error)
	Update(ctx context.Context, req model.Label) (model.Label, error)
	Delete(ctx context.Context, id int64) error
}

func New(db *sqlx.DB) Repo {
	return repo{
		db: db,
	}
}

func (r repo) Create(ctx context.Context, req model.Label) (model.Label, error) {
	query := `INSERT INTO labels (name) 
	          VALUES ($1) RETURNING id`

	var id int64

	err := r.db.QueryRowContext(ctx, query, req.Name).Scan(&id)
	if err != nil {
		log.Println(err)
		if pqErr, ok := err.(*pq.Error); ok && pqErr.Code == "23505" {
			return model.Label{}, ErrConstraintsCheck
		}

		return model.Label{}, fmt.Errorf("failed to create Repo: %w", err)
	}

	req.ID = id

	return req, nil
}

func (r repo) Delete(ctx context.Context, id int64) error {
	query := `DELETE FROM labels WHERE id = $1`

	result, err := r.db.ExecContext(ctx, query, id)
	if err != nil {
		log.Println("Error executing DELETE query:", err)
		return fmt.Errorf("failed to delete Repo: %w", err)
	}

	rowsAffected, err := result.RowsAffected()
	if err != nil {
		log.Println("Error getting rows affected:", err)
		return fmt.Errorf("failed to check rows affected: %w", err)
	}

	if rowsAffected == 0 {
		log.Println("No Repo found with ID:", id)
		return ErrCommentNotFound
	}

	return nil
}

func (r repo) Get(ctx context.Context, id int64) (model.Label, error) {
	var result model.Label

	query := `SELECT * FROM labels WHERE id = $1`

	err := r.db.GetContext(ctx, &result, query, id)
	if err != nil {
		if errors.Is(err, sql.ErrNoRows) {
			return result, ErrCommentNotFound
		}
		return result, fmt.Errorf("failed to retrieve Repo by ID: %w", err)
	}

	return result, nil
}

func (r repo) GetList(ctx context.Context) ([]model.Label, error) {
	var result []model.Label

	query := `SELECT * FROM labels`

	err := r.db.SelectContext(ctx, &result, query)
	if err != nil {
		return nil, fmt.Errorf("failed to retrieve result: %w", err)
	}

	if len(result) == 0 {
		return []model.Label{}, nil
	}

	return result, nil
}

func (r repo) Update(ctx context.Context, req model.Label) (model.Label, error) {
	query := `UPDATE labels SET name = $1
	          WHERE id = $2 RETURNING id, name`

	var result model.Label

	err := r.db.QueryRowContext(ctx, query, req.Name, req.ID).Scan(&result.ID, &result.Name)
	if err != nil {
		if errors.Is(err, sql.ErrNoRows) {
			log.Println("Repo not found with id:", req.ID)
			return result, ErrCommentNotFound
		}

		log.Println("error with query:", err)
		return result, fmt.Errorf("failed to update Repo: %w", err)
	}

	return result, nil
}
