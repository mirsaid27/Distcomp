package httpapi

import (
	"encoding/json"
	"net/http"
	"strconv"

	"github.com/go-chi/chi/v5"

	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/repository/psql"
	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/repository/psql/generated"
	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/service"
	"github.com/strcarne/distributed-calculations/internal/entity"
)

type tweetHandlerFunc func(w http.ResponseWriter, r *http.Request, tweetService service.Tweet)

func NewTweetRouter(queries *generated.Queries) *chi.Mux {
	r := chi.NewRouter()

	tweetRepository := psql.NewTweetRepository(queries)
	tweetService := service.NewTweet(tweetRepository)

	r.Route("/", func(r chi.Router) {
		r.Get("/", wrapTweetHandler(GetTweets, tweetService))
		r.Get("/{id}", wrapTweetHandler(GetTweet, tweetService))
		r.Post("/", wrapTweetHandler(CreateTweet, tweetService))
		r.Delete("/{id}", wrapTweetHandler(DeleteTweet, tweetService))
		r.Put("/", wrapTweetHandler(UpdateTweet, tweetService))
	})

	return r
}

func wrapTweetHandler(handler tweetHandlerFunc, tweetService service.Tweet) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		handler(w, r, tweetService)
	}
}

func GetTweets(w http.ResponseWriter, r *http.Request, tweetService service.Tweet) {
	tweets, err := tweetService.GetAllTweets()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	json.NewEncoder(w).Encode(tweets)
}

func GetTweet(w http.ResponseWriter, r *http.Request, tweetService service.Tweet) {
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

func CreateTweet(w http.ResponseWriter, r *http.Request, tweetService service.Tweet) {
	var tweet entity.Tweet
	if err := json.NewDecoder(r.Body).Decode(&tweet); err != nil {
		w.WriteHeader(http.StatusForbidden)
		json.NewEncoder(w).Encode(tweet)
		return
	}

	tweet, err := tweetService.CreateTweet(tweet)
	if err != nil {
		w.WriteHeader(http.StatusForbidden)
		json.NewEncoder(w).Encode(tweet)
		return
	}

	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(tweet)
}

func DeleteTweet(w http.ResponseWriter, r *http.Request, tweetService service.Tweet) {
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

func UpdateTweet(w http.ResponseWriter, r *http.Request, tweetService service.Tweet) {
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
