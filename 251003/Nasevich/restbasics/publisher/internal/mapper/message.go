package mapper

import (
	message "github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/publisher/internal/model"
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/publisher/internal/storage/model"
)

func MapMessageToModel(i message.Message) model.Message {
	return model.Message{
		ID:      int64(i.ID),
		IssueID: int64(i.IssueID),
		Content: i.Content,
	}
}

func MapModelToMessage(i model.Message) message.Message {
	return message.Message{
		ID:      int(i.ID),
		IssueID: int(i.IssueID),
		Content: i.Content,
	}
}
