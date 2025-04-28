package handler

import (
	"encoding/json"
	"net/http"
	"strconv"

	"github.com/go-chi/chi/v5"
	"github.com/strcarne/task310-rest/internal/entity"
	"github.com/strcarne/task310-rest/internal/repository/inmemory"
	"github.com/strcarne/task310-rest/internal/service"
)

var tweetService = service.NewTweet(inmemory.NewTweetRepository())

func NewTweetRouter() *chi.Mux {
	r := chi.NewRouter()

	r.Route("/", func(r chi.Router) {
		r.Get("/", GetTweets)
		r.Get("/{id}", GetTweet)
		r.Post("/", CreateTweet)
		r.Delete("/{id}", DeleteTweet)
		r.Put("/", UpdateTweet)
	})

	return r
}

func GetTweets(w http.ResponseWriter, r *http.Request) {
	tweets := tweetService.GetAllTweets()
	json.NewEncoder(w).Encode(tweets)
}

func GetTweet(w http.ResponseWriter, r *http.Request) {
	idStr := chi.URLParam(r, "id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	tweet, err := tweetService.GetTweetByID(id)
	if err != nil {
		http.Error(w, err.Error(), http.StatusNotFound)
		return
	}

	json.NewEncoder(w).Encode(tweet)
}

func CreateTweet(w http.ResponseWriter, r *http.Request) {
	var tweet entity.Tweet
	if err := json.NewDecoder(r.Body).Decode(&tweet); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	tweet = tweetService.CreateTweet(tweet)
	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(tweet)
}

func DeleteTweet(w http.ResponseWriter, r *http.Request) {
	idStr := chi.URLParam(r, "id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	if tweet, err := tweetService.DeleteTweet(id); err != nil {
		w.WriteHeader(http.StatusNotFound)
		json.NewEncoder(w).Encode(tweet)
		return
	}

	w.WriteHeader(http.StatusNoContent)
}

func UpdateTweet(w http.ResponseWriter, r *http.Request) {
	var tweet entity.Tweet
	if err := json.NewDecoder(r.Body).Decode(&tweet); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	if err := tweetService.UpdateTweet(tweet); err != nil {
		w.WriteHeader(http.StatusNotFound)
		json.NewEncoder(w).Encode(tweet)
		return
	}

	w.WriteHeader(http.StatusOK)
	json.NewEncoder(w).Encode(tweet)
}
