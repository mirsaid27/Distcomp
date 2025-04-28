package inmemory

import (
	"errors"
	"sync/atomic"

	"github.com/strcarne/task310-rest/internal/entity"
	"github.com/strcarne/task310-rest/pkg/container"
)

var ErrLabelNotFound = errors.New("label not found")

type LabelRepository struct {
	storage *container.InMemoryStorage[int64, entity.Label]

	// counter is incremeted field that will be id of new labellr.
	counter atomic.Int64
}

func NewLabelRepository() *LabelRepository {
	return &LabelRepository{
		storage: container.DefaultInMemoryStorage[int64, entity.Label](),
		counter: atomic.Int64{},
	}
}

func (lr *LabelRepository) createNewID() int64 {
	return lr.counter.Add(1)
}

func (lr *LabelRepository) CreateLabel(label entity.Label) int64 {
	newID := lr.createNewID()
	label.ID = newID

	lr.storage.Set(newID, label)

	return newID
}

func (lr *LabelRepository) DeleteLabel(id int64) (entity.Label, error) {
	label, ok := lr.storage.GetWithOK(id)
	if !ok {
		return label, ErrLabelNotFound
	}

	lr.storage.Delete(id)

	return label, nil
}

func (lr *LabelRepository) GetAllLabels() []entity.Label {
	return lr.storage.GetAll()
}

func (lr *LabelRepository) GetLabelByID(id int64) (entity.Label, error) {
	label, ok := lr.storage.GetWithOK(id)
	if !ok {
		return label, ErrLabelNotFound
	}

	return label, nil
}

func (lr *LabelRepository) UpdateLabel(label entity.Label) error {
	if !lr.storage.Has(label.ID) {
		return ErrLabelNotFound
	}

	lr.storage.Set(label.ID, label)

	return nil
}
