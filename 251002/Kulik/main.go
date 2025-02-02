package main

import (
	"fmt"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"

	"distributedcomputing/controllers"
	"distributedcomputing/model"
	"distributedcomputing/service"
	"distributedcomputing/storage"
	"net/http"
	"strconv"
)


func main() {
	creatorRepo := storage.NewInMemStorage[model.Creator]()
	storyRepo := storage.NewInMemStorage[model.Story]()
	// noteRepo := storage.NewInMemStorage[model.Note]()
	// markRepo := storage.NewInMemStorage[model.Mark]()

	creatorService := service.NewCreatorService(creatorRepo)
	storyService := service.NewStoryService(storyRepo)
	// noteService := service.NewNoteService(noteRepo)
	// markService := service.NewMarkService(markRepo)

	creatorController := controllers.NewCreatorController(creatorService)
	storyController := controllers.NewStoryController(storyService)




	e := echo.New()
	e.Use(middleware.Recover())

	e.POST("/api/v1.0/creators", creatorController.Create)
	e.GET("/api/v1.0/creators/:id", creatorController.Get)
	e.PUT("/api/v1.0/creators", creatorController.Update)
	e.DELETE("/api/v1.0/creators/:id", creatorController.Delete)

	e.POST("/api/v1.0/stories", storyController.Create)
	e.GET("/api/v1.0/stories/:id", storyController.Get)
	e.PUT("/api/v1.0/stories", storyController.Update)
	e.DELETE("/api/v1.0/stories/:id", storyController.Delete)

	// controller.RegisterRoutes(e, creatorService, storyService, noteService, markService)

	e.Logger.Fatal(e.Start(":24110"))
}
