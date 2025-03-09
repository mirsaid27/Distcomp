package http

import (
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/handler/http/issue"

	"github.com/gorilla/mux"
)

const pathPrefix = "api/v1.0"

func New() *mux.Router {
	r := mux.NewRouter()
	r.PathPrefix(pathPrefix)

	issue.InitRoutes(r)

	return r
}
