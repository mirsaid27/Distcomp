package handler

import (
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/handler/http"

	"github.com/gorilla/mux"
)

type Handler struct {
	HTTP *mux.Router
}

func New() Handler {
	return Handler{
		HTTP: http.New(),
	}
}
