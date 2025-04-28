package service

import (
	"github.com/strcarne/task310-rest/internal/entity"
)

type Label struct {
	repo LabelRepository
}

type LabelRepository interface {
	CreateLabel(label entity.Label) int64
	DeleteLabel(id int64) (entity.Label, error)
	GetAllLabels() []entity.Label
	GetLabelByID(id int64) (entity.Label, error)
	UpdateLabel(label entity.Label) error
}

func NewLabel(repo LabelRepository) Label {
	return Label{
		repo: repo,
	}
}

func (u Label) CreateLabel(label entity.Label) entity.Label {
	id := u.repo.CreateLabel(label)

	label.ID = id

	return label
}

func (u Label) DeleteLabel(id int64) (entity.Label, error) {
	return u.repo.DeleteLabel(id)
}

func (u Label) GetAllLabels() []entity.Label {
	return u.repo.GetAllLabels()
}

func (u Label) GetLabelByID(id int64) (entity.Label, error) {
	return u.repo.GetLabelByID(id)
}

func (u Label) UpdateLabel(label entity.Label) error {
	return u.repo.UpdateLabel(label)
}
