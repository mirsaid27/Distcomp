package handler

import (
	"net/http"

	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/discussion/internal/handler/http/message"
	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/discussion/internal/service"
	"github.com/gin-gonic/gin"
)

func New(svc service.Service) http.Handler {
	engine := gin.Default()

	router := engine.Group("/api")
	{
		message.New(svc).InitRoutes(router)
	}

	return engine
}
