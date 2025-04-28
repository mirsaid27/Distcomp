package handler

import (
	"net/http"

	"github.com/go-chi/chi/v5"
)

func RegisterV1(router chi.Router) {
	router.Route("/api/v1.0", func(r chi.Router) {
		r.Mount("/users", NewUserRouter())
		r.Mount("/labels", NewLabelRouter())
		r.Mount("/tweets", NewTweetRouter())
		r.Mount("/notes", NewNoteRouter())
	})
}

func handleError(w http.ResponseWriter, statusCode int, err error) {
	w.WriteHeader(statusCode)

	_, _ = w.Write([]byte(err.Error()))
}
