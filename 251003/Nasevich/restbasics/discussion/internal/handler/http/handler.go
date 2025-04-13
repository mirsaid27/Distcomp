package http

import (
	"github.com/gorilla/mux"
)

const pathPrefix = "/api/v1.0"

func New() *mux.Router {
	r := mux.NewRouter()
	//api := r.PathPrefix(pathPrefix).Subrouter()

	return r
}
