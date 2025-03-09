package service

import (
	"context"

	creator "github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/model"
)

type CreatorService interface {
	CreateCreator(ctx context.Context, cr creator.Creator) (creator.Creator, error)
	GetCreators(ctx context.Context) ([]creator.Creator, error)
	GetCreatorByID(ctx context.Context, id int) (creator.Creator, error)
	UpdateCreatorByID(ctx context.Context, cr creator.Creator) (creator.Creator, error)
	DeleteCreatorByID(ctx context.Context, id int) error
}
