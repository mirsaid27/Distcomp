package service

import (
	"errors"

	"github.com/strcarne/task310-rest/internal/entity"
)

var (
	ErrInvalidTitle   = errors.New("invalid title")
	ErrInvalidContent = errors.New("invalid content")
)

type Tweet struct {
	repo TweetRepository
}

type TweetRepository interface {
	CreateTweet(tweet entity.Tweet) int64
	DeleteTweet(id int64) (entity.Tweet, error)
	GetAllTweets() []entity.Tweet
	GetTweetByID(id int64) (entity.Tweet, error)
	UpdateTweet(tweet entity.Tweet) error
}

func NewTweet(repo TweetRepository) Tweet {
	return Tweet{
		repo: repo,
	}
}

func (u Tweet) CreateTweet(tweet entity.Tweet) entity.Tweet {
	id := u.repo.CreateTweet(tweet)

	tweet.ID = id

	return tweet
}

func (u Tweet) DeleteTweet(id int64) (entity.Tweet, error) {
	return u.repo.DeleteTweet(id)
}

func (u Tweet) GetAllTweets() []entity.Tweet {
	return u.repo.GetAllTweets()
}

func (u Tweet) GetTweetByID(id int64) (entity.Tweet, error) {
	return u.repo.GetTweetByID(id)
}

func (u Tweet) UpdateTweet(tweet entity.Tweet) error {
	if len(tweet.Title) < 2 {
		return ErrInvalidTitle
	}

	if len(tweet.Content) < 4 {
		return ErrInvalidContent
	}

	return u.repo.UpdateTweet(tweet)
}
