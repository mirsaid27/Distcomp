package httpapi

import (
	"github.com/go-chi/chi/v5"
	"github.com/gocql/gocql"
)

func RegisterV1(router chi.Router, session *gocql.Session) {
	router.Route("/api/v1.0", func(r chi.Router) {
		r.Mount("/notes", NewNoteRouter(session))
	})
}
