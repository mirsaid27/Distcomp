package http

import (
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/handler/http/creator"
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/service"
	"github.com/gorilla/mux"
)

const pathPrefix = "/api/v1.0"

func New(srv service.Service) *mux.Router {
	r := mux.NewRouter()
	api := r.PathPrefix(pathPrefix).Subrouter()

	creator := creator.New(srv.Creator)
	creator.InitRoutes(api)

	return r
}
