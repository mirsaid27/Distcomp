package model

type NoteRequestTo struct {
	Id      int64  `json:"id" bson:"id,omitempty"`
	StoryID int64  `json:"storyId" bson:"story_id"`
	Content string `json:"content" bson:"content"`
}

type NoteResponseTo struct {
	Id      int64  `json:"id" bson:"id,omitempty"`
	StoryID int64  `json:"storyId" bson:"story_id"`
	Content string `json:"content" bson:"content"`
}
