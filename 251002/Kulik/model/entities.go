package model

import "time"

type Creator struct {
	Id        int64
	Login     string
	Password  string
	FirstName string
	LastName  string
}

type Story struct {
	Id        int64
	CreatorID int64
	Title     string
	Content   string
	Created   time.Time
	Modified  time.Time
}

type Note struct {
	Id      int64
	StoryID int64
	Content string
}

type Mark struct {
	Id   int64
	Name string
}
