package message

import (
	"context"
	"log"

	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/publisher/internal/mapper"
	messageModel "github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/publisher/internal/model"
	dbMessageModel "github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/publisher/internal/storage/model"
)

type service struct {
	db MessageDB
}

type MessageDB interface {
	CreateMessage(ctx context.Context, message dbMessageModel.Message) (dbMessageModel.Message, error)
	GetMessages(ctx context.Context) ([]dbMessageModel.Message, error)
	GetMessageByID(ctx context.Context, id int64) (dbMessageModel.Message, error)
	UpdateMessageByID(ctx context.Context, message dbMessageModel.Message) (dbMessageModel.Message, error)
	DeleteMessageByID(ctx context.Context, id int64) error
}

type MessageService interface {
	CreateMessage(ctx context.Context, message messageModel.Message) (messageModel.Message, error)
	GetMessages(ctx context.Context) ([]messageModel.Message, error)
	GetMessageByID(ctx context.Context, id int64) (messageModel.Message, error)
	UpdateMessageByID(ctx context.Context, message messageModel.Message) (messageModel.Message, error)
	DeleteMessageByID(ctx context.Context, id int64) error
}

func New(db MessageDB) MessageService {
	return &service{
		db: db,
	}
}

func (s *service) CreateMessage(ctx context.Context, message messageModel.Message) (messageModel.Message, error) {
	m, err := s.db.CreateMessage(ctx, mapper.MapMessageToModel(message))
	if err != nil {
		return messageModel.Message{}, err
	}

	log.Println(m)

	return mapper.MapModelToMessage(m), nil
}

func (s *service) GetMessages(ctx context.Context) ([]messageModel.Message, error) {
	var mappedMessages []messageModel.Message

	msgs, err := s.db.GetMessages(ctx)
	if err != nil {
		return mappedMessages, err
	}

	for _, msg := range msgs {
		mappedMessages = append(mappedMessages, mapper.MapModelToMessage(msg))
	}

	if len(mappedMessages) == 0 {
		return []messageModel.Message{}, nil
	}

	return mappedMessages, nil
}

func (s *service) GetMessageByID(ctx context.Context, id int64) (messageModel.Message, error) {
	msg, err := s.db.GetMessageByID(ctx, id)
	if err != nil {
		return messageModel.Message{}, err
	}

	return mapper.MapModelToMessage(msg), nil
}

func (s *service) UpdateMessageByID(ctx context.Context, message messageModel.Message) (messageModel.Message, error) {
	msg, err := s.db.UpdateMessageByID(ctx, mapper.MapMessageToModel(message))
	if err != nil {
		return messageModel.Message{}, err
	}

	return mapper.MapModelToMessage(msg), nil
}

func (s *service) DeleteMessageByID(ctx context.Context, id int64) error {
	return s.db.DeleteMessageByID(ctx, id)
}
