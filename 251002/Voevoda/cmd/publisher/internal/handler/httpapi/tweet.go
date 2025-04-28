package httpapi

import (
	"encoding/json"
	"net/http"
	"strconv"

	"github.com/go-chi/chi/v5"

	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/di"
	"github.com/strcarne/distributed-calculations/internal/entity"
	"github.com/strcarne/distributed-calculations/internal/entity/server"
)

func NewTweetRouter(deps di.Container) *chi.Mux {
	r := chi.NewRouter()

	r.Route("/", func(r chi.Router) {
		r.Get("/", wrapHandler(GetTweets, deps))
		r.Get("/{id}", wrapHandler(GetTweet, deps))
		r.Post("/", wrapHandler(CreateTweet, deps))
		r.Delete("/{id}", wrapHandler(DeleteTweet, deps))
		r.Put("/", wrapHandler(UpdateTweet, deps))
	})

	return r
}

func GetTweets(w http.ResponseWriter, r *http.Request, deps di.Container) *server.Error {
	tweets, err := deps.Services.Tweet.GetAllTweets()
	if err != nil {
		return server.NewError(err, http.StatusInternalServerError)
	}

	json.NewEncoder(w).Encode(tweets)
	deps.Logger.Info("tweets retrieved", "count", len(tweets))

	return nil
}

func GetTweet(w http.ResponseWriter, r *http.Request, deps di.Container) *server.Error {
	idStr := chi.URLParam(r, "id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return server.NewError(err, http.StatusBadRequest)
	}

	tweet, err := deps.Services.Tweet.GetTweetByID(id)
	if err != nil {
		return server.NewError(err, http.StatusNotFound)
	}

	json.NewEncoder(w).Encode(tweet)
	deps.Logger.Info("tweet retrieved", "tweet_id", tweet.ID)

	return nil
}

func CreateTweet(w http.ResponseWriter, r *http.Request, deps di.Container) *server.Error {
	var tweet entity.Tweet
	if err := json.NewDecoder(r.Body).Decode(&tweet); err != nil {
		return server.NewError(err, http.StatusForbidden)
	}

	tweet, err := deps.Services.Tweet.CreateTweet(tweet)
	if err != nil {
		return server.NewError(err, http.StatusForbidden)
	}

	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(tweet)
	deps.Logger.Info("tweet created", "tweet_id", tweet.ID)

	return nil
}

func DeleteTweet(w http.ResponseWriter, r *http.Request, deps di.Container) *server.Error {
	idStr := chi.URLParam(r, "id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return server.NewError(err, http.StatusBadRequest)
	}

	if _, err := deps.Services.Tweet.DeleteTweet(id); err != nil {
		return server.NewError(err, http.StatusNotFound)
	}

	w.WriteHeader(http.StatusNoContent)
	deps.Logger.Info("tweet deleted", "tweet_id", id)

	return nil
}

func UpdateTweet(w http.ResponseWriter, r *http.Request, deps di.Container) *server.Error {
	var tweet entity.Tweet
	if err := json.NewDecoder(r.Body).Decode(&tweet); err != nil {
		return server.NewError(err, http.StatusBadRequest)
	}

	if err := deps.Services.Tweet.UpdateTweet(tweet); err != nil {
		return server.NewError(err, http.StatusNotFound)
	}

	w.WriteHeader(http.StatusOK)
	json.NewEncoder(w).Encode(tweet)
	deps.Logger.Info("tweet updated", "tweet_id", tweet.ID)

	return nil
}
