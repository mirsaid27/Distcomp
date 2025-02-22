package main

import (
	// "fmt"

	"log"
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"

	"distributedcomputing/controllers"
	"github.com/jmoiron/sqlx"
	"distributedcomputing/service"
	"distributedcomputing/storage"
	_ "github.com/lib/pq"
	// "net/http"
	// "strconv"
)


func main() {
	// creatorRepo := storage.NewInMemStorage[model.Creator]()
	// storyRepo := storage.NewInMemStorage[model.Story]()
	// noteRepo := storage.NewInMemStorage[model.Note]()
	// markRepo := storage.NewInMemStorage[model.Mark]()

	db, err := sqlx.Connect("postgres", "user=postgres dbname=distcomp sslmode=disable")
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	
	creatorRepo := storage.NewCreatorStorage(db)
	storyRepo := storage.NewStoryStorage(db)
	noteRepo := storage.NewNoteStorage(db)
	markRepo := storage.NewMarkStorage(db)

	creatorService := service.NewCreatorService(creatorRepo)
	storyService := service.NewStoryService(storyRepo)
	noteService := service.NewNoteService(noteRepo)
	markService := service.NewMarkService(markRepo)

	creatorController := controllers.NewCreatorController(creatorService)
	storyController := controllers.NewStoryController(storyService)
	noteController := controllers.NewNoteController(noteService)
	markController := controllers.NewMarkController(markService)


	e := echo.New()
	e.Use(middleware.Recover())

	e.POST("/api/v1.0/creators", creatorController.Create)
	e.GET("/api/v1.0/creators", creatorController.GetAll)
	e.PUT("/api/v1.0/creators", creatorController.Update)
	e.DELETE("/api/v1.0/creators/:id", creatorController.Delete)
	e.GET("/api/v1.0/creators/:id", creatorController.Get)

	e.POST("/api/v1.0/stories", storyController.Create)
	e.PUT("/api/v1.0/stories", storyController.Update)
	e.GET("/api/v1.0/stories", storyController.GetAll)
	e.DELETE("/api/v1.0/stories/:id", storyController.Delete)
	e.GET("/api/v1.0/stories/:id", storyController.Get)

	e.POST("/api/v1.0/notes", noteController.Create)
	e.PUT("/api/v1.0/notes", noteController.Update)
	e.GET("/api/v1.0/notes", noteController.GetAll)
	e.DELETE("/api/v1.0/notes/:id", noteController.Delete)
	e.GET("/api/v1.0/notes/:id", noteController.Get)

	e.POST("/api/v1.0/marks", markController.Create)
	e.PUT("/api/v1.0/marks", markController.Update)
	e.GET("/api/v1.0/marks", markController.GetAll)
	e.DELETE("/api/v1.0/marks/:id", markController.Delete)
	e.GET("/api/v1.0/marks/:id", markController.Get)

	e.Logger.Fatal(e.Start(":24110"))
}
