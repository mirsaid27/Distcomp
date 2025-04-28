package service

import (
	"github.com/strcarne/task310-rest/internal/entity"
)

type Note struct {
	repo NoteRepository
}

type NoteRepository interface {
	CreateNote(note entity.Note) int64
	DeleteNote(id int64) (entity.Note, error)
	GetAllNotes() []entity.Note
	GetNoteByID(id int64) (entity.Note, error)
	UpdateNote(note entity.Note) error
}

func NewNote(repo NoteRepository) Note {
	return Note{
		repo: repo,
	}
}

func (u Note) CreateNote(note entity.Note) entity.Note {
	id := u.repo.CreateNote(note)

	note.ID = id

	return note
}

func (u Note) DeleteNote(id int64) (entity.Note, error) {
	return u.repo.DeleteNote(id)
}

func (u Note) GetAllNotes() []entity.Note {
	return u.repo.GetAllNotes()
}

func (u Note) GetNoteByID(id int64) (entity.Note, error) {
	return u.repo.GetNoteByID(id)
}

func (u Note) UpdateNote(note entity.Note) error {
	return u.repo.UpdateNote(note)
}
