package psql

import (
	"log"

	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/storage/psql/creator"

	"github.com/jmoiron/sqlx"
	_ "github.com/lib/pq"
)

type PSQL struct {
	db *sqlx.DB

	CreatorInst creator.Creator
}

func New() (*PSQL, error) {
	cfg := NewConfig()

	db, err := sqlx.Connect("postgres", cfg.DSN())
	if err != nil {
		return nil, err
	}

	log.Println("Connected to PostgreSQL")

	return &PSQL{
		db: db,

		CreatorInst: creator.New(db),
	}, nil
}

func (p *PSQL) Close() {
	if err := p.db.Close(); err != nil {
		log.Println("Error closing DB:", err)
	}
}
