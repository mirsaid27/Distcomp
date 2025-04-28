package service

import (
	"errors"

	"github.com/strcarne/task310-rest/internal/entity"
)

var (
	ErrInvalidLogin    = errors.New("invalid login")
	ErrInvalidPassword = errors.New("invalid password")
)

type User struct {
	repo UserRepository
}

type UserRepository interface {
	CreateUser(user entity.User) int64
	DeleteUser(id int64) (entity.User, error)
	GetAllUsers() []entity.User
	GetUserByID(id int64) (entity.User, error)
	UpdateUser(user entity.User) error
}

func NewUser(repo UserRepository) User {
	return User{
		repo: repo,
	}
}

func (u User) CreateUser(user entity.User) entity.User {
	id := u.repo.CreateUser(user)

	user.ID = id

	return user
}

func (u User) DeleteUser(id int64) (entity.User, error) {
	return u.repo.DeleteUser(id)
}

func (u User) GetAllUsers() []entity.User {
	return u.repo.GetAllUsers()
}

func (u User) GetUserByID(id int64) (entity.User, error) {
	return u.repo.GetUserByID(id)
}

func (u User) UpdateUser(user entity.User) error {
	if len(user.Login) < 2 {
		return ErrInvalidLogin
	}

	if len(user.Password) < 8 {
		return ErrInvalidPassword
	}

	return u.repo.UpdateUser(user)
}
