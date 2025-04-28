package handler

import (
	"net/http"

	"github.com/go-chi/chi/v5"
	"github.com/strcarne/task310-rest/internal/repository/psql/generated"
)

func RegisterV1(router chi.Router, queries *generated.Queries) {
	router.Route("/api/v1.0", func(r chi.Router) {
		r.Mount("/users", NewUserRouter(queries))
		r.Mount("/labels", NewLabelRouter(queries))
		r.Mount("/tweets", NewTweetRouter(queries))
		r.Mount("/notes", NewNoteRouter(queries))
	})
}

func handleError(w http.ResponseWriter, statusCode int, err error) {
	w.WriteHeader(statusCode)

	_, _ = w.Write([]byte(err.Error()))
}
