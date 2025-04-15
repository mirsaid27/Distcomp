package model

type Message struct {
	ID      int64  `db:"id"      json:"id"`
	IssueID int64  `db:"news_id" json:"issueId"`
	Content string `db:"content" json:"content"`
}
