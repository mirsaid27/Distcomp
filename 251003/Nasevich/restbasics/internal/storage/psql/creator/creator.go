package creator

import (
	"context"
	"fmt"

	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/storage/model"

	"github.com/jmoiron/sqlx"
)

type Creator interface {
	CreateCreator(ctx context.Context, cr model.Creator) (int64, error)
}

type instance struct {
	db *sqlx.DB
}

func New(db *sqlx.DB) Creator {
	return &instance{
		db: db,
	}
}

func (i *instance) CreateCreator(ctx context.Context, cr model.Creator) (int64, error) {
	query := `INSERT INTO Creator (login, password, firstname, lastname) 
	          VALUES ($1, $2, $3, $4) RETURNING id`

	var id int64
	err := i.db.QueryRowContext(ctx, query, cr.Login, cr.Password, cr.FirstName, cr.LastName).Scan(&id)
	if err != nil {
		return 0, fmt.Errorf("failed to create creator: %w", err)
	}

	return id, nil
}
