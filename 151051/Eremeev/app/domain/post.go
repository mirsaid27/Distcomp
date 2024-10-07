package domain

import (
	e "DC-eremeev/app/errors"
	"DC-eremeev/db"
	"errors"

	"gorm.io/gorm"
)

type Post struct {
	ID      uint   `json:"id"`
	IssueID uint   `json:"issueId"`
	Content string `json:"content"`
}

// GORM
func (Post) TableName() string {
	return "tbl_post"
}

// IDAO
func (post *Post) Exist() bool {
	exist := db.Connection().First(&Post{}, post.ID)
	return exist.Error == nil
}

func (post *Post) Save() error {
	validator := NewPostValidator(post)
	if !validator.Validate() {
		return validator.Error()
	}
	err := db.Connection().Save(post).Error
	if err != nil && errors.Is(err, gorm.ErrDuplicatedKey) {
		return &e.ErrUniqueKeyViolation{}
	}
	return err
}

func (post *Post) Find(id uint) error {
	err := db.Connection().First(post, id).Error
	if err != nil && errors.Is(err, gorm.ErrRecordNotFound) {
		return &e.ErrNotFound{}
	}
	return err
}

func (post *Post) Delete() {
	db.Connection().Delete(&Post{}, post.ID)
}
