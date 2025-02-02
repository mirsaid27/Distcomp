package service

import (
	"distributedcomputing/model"
	"distributedcomputing/storage"
)

type CreatorService struct {
	store storage.IStorage[model.Creator]
}

func NewCreatorService(store storage.IStorage[model.Creator]) *CreatorService {
	return &CreatorService{store: store}
}

func (s *CreatorService) Create(dto model.CreatorRequestTo) (model.CreatorResponseTo, error) {
	creator := model.CreatorToModel(dto)
	id, err := s.store.Create(creator)
	creator.Id = id
	if err != nil {
		return model.CreatorResponseTo{}, err
	}
	return model.CreatorToDTO(creator), nil
}

func (s *CreatorService) Get(id int64) (model.CreatorResponseTo, error) {
	creator, err := s.store.Get(id)
	creator.Id = id
	if err != nil {
		return model.CreatorResponseTo{}, err
	}
	return model.CreatorToDTO(creator), nil
}

func (s *CreatorService) Update(dto model.CreatorRequestTo) error {
	creator := model.CreatorToModel(dto)
	return s.store.Update(creator.Id, creator)
}

func (s *CreatorService) Delete(id int64) error {
	return s.store.Delete(id)
}

type StoryService struct {
	store storage.IStorage[model.Story]
}

func NewStoryService(store storage.IStorage[model.Story]) *StoryService {
	return &StoryService{store: store}
}


func (s *StoryService) Create(dto model.StoryRequestTo) (model.StoryResponseTo, error) {
	story := model.StoryToModel(dto)
	id, err := s.store.Create(story)
	story.Id = id
	if err != nil {
		return model.StoryResponseTo{}, err
	}
	return model.StoryToDTO(story), nil
}

func (s *StoryService) Get(id int64) (model.StoryResponseTo, error) {
	story, err := s.store.Get(id)
	story.Id = id
	if err != nil {
		return model.StoryResponseTo{}, err
	}
	return model.StoryToDTO(story), nil
}

func (s *StoryService) Update(dto model.StoryRequestTo) error {
	story := model.StoryToModel(dto)
	return s.store.Update(story.Id, story)
}

func (s *StoryService) Delete(id int64) error {
	return s.store.Delete(id)
}

type NoteService struct {
	store storage.IStorage[model.Note]
}

func NewNoteService(store storage.IStorage[model.Note]) *NoteService {
	return &NoteService{store: store}
}

func (s *NoteService) Create(dto model.NoteRequestTo) (model.NoteResponseTo, error) {
	note := model.NoteToModel(dto)
	id, err := s.store.Create(note)
	note.Id = id
	if err != nil {
		return model.NoteResponseTo{}, err
	}
	return model.NoteToDTO(note), nil
}

func (s *NoteService) Get(id int64) (model.NoteResponseTo, error) {
	note, err := s.store.Get(id)
	note.Id = id
	if err != nil {
		return model.NoteResponseTo{}, err
	}
	return model.NoteToDTO(note), nil
}

func (s *NoteService) Update(dto model.NoteRequestTo) error {
	note := model.NoteToModel(dto)
	return s.store.Update(note.Id, note)
}

func (s *NoteService) Delete(id int64) error {
	return s.store.Delete(id)
}

type MarkService struct {
	store storage.IStorage[model.Mark]
}

func NewMarkService(store storage.IStorage[model.Mark]) *MarkService {
	return &MarkService{store: store}
}

func (s *MarkService) Create(dto model.MarkRequestTo) (model.MarkResponseTo, error) {
	mark := model.MarkToModel(dto)
	id, err := s.store.Create(mark)
	mark.Id = id
	if err != nil {
		return model.MarkResponseTo{}, err
	}
	return model.MarkToDTO(mark), nil
}

func (s *MarkService) Get(id int64) (model.MarkResponseTo, error) {
	mark, err := s.store.Get(id)
	mark.Id = id
	if err != nil {
		return model.MarkResponseTo{}, err
	}
	return model.MarkToDTO(mark), nil
}

func (s *MarkService) Update(dto model.MarkRequestTo) error {
	mark := model.MarkToModel(dto)
	return s.store.Update(mark.Id, mark)
}

func (s *MarkService) Delete(id int64) error {
	return s.store.Delete(id)
}