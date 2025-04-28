package psql

import (
	"context"
	"database/sql"
	"errors"

	"github.com/strcarne/task310-rest/internal/entity"
	"github.com/strcarne/task310-rest/internal/repository/psql/generated"
)

var (
	ErrNoteNotFound = errors.New("note not found")
)

type NoteRepository struct {
	queries *generated.Queries
}

func NewNoteRepository(queries *generated.Queries) *NoteRepository {
	if queries == nil {
		panic("queries is nil")
	}

	return &NoteRepository{
		queries: queries,
	}
}

func (repo *NoteRepository) GetAllNotes() ([]entity.Note, error) {
	notes, err := repo.queries.GetAllNotes(context.Background())
	if err != nil {
		return nil, err
	}

	convertedNotes := make([]entity.Note, 0, len(notes))
	for _, note := range notes {
		tweetID := 0
		if note.Tweetid != nil {
			tweetID = int(*note.Tweetid)
		}
		convertedNotes = append(convertedNotes, entity.Note{
			ID:      note.ID,
			TweetID: tweetID,
			Content: note.Content,
		})
	}

	return convertedNotes, nil
}

func (repo *NoteRepository) GetNoteByID(id int64) (entity.Note, error) {
	note, err := repo.queries.GetNoteByID(context.Background(), id)
	if err != nil {
		if err == sql.ErrNoRows {
			return entity.Note{}, ErrNoteNotFound
		}
		return entity.Note{}, err
	}
	tweetID := 0
	if note.Tweetid != nil {
		tweetID = int(*note.Tweetid)
	}
	return entity.Note{
		ID:      note.ID,
		TweetID: tweetID,
		Content: note.Content,
	}, nil
}

func (repo *NoteRepository) CreateNote(note entity.Note) (int64, error) {
	tweetID := int64(note.TweetID)

	createdNote, err := repo.queries.CreateNote(context.Background(), generated.CreateNoteParams{
		Tweetid: &tweetID,
		Content: note.Content,
	})
	if err != nil {
		return 0, err
	}

	return createdNote.ID, nil
}

func (repo *NoteRepository) DeleteNote(id int64) (entity.Note, error) {
	note, err := repo.GetNoteByID(id)
	if err != nil {
		return entity.Note{}, err
	}

	if err := repo.queries.DeleteNote(context.Background(), id); err != nil {
		return entity.Note{}, err
	}

	return note, nil
}

func (repo *NoteRepository) UpdateNote(note entity.Note) error {
	tweetID := int64(note.TweetID)
	return repo.queries.UpdateNote(context.Background(), generated.UpdateNoteParams{
		ID:      note.ID,
		Tweetid: &tweetID,
		Content: note.Content,
	})
}
