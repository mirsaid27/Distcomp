package storage

import (
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/storage/psql"
)

func New() (*psql.PSQL, error) {
	return psql.New()
}
