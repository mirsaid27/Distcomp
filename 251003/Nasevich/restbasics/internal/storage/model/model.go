package model

type Creator struct {
	ID        int64  `db:"id"`
	Login     string `db:"login"`
	Password  string `db:"password"`
	FirstName string `db:"firstname"`
	LastName  string `db:"lastname"`
}
