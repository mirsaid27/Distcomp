package main

import (
	"log"
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"

	"distributedcomputing/controllers"
	// "distributedcomputing/model"
	"distributedcomputing/service"
	"distributedcomputing/storage"

	"github.com/jmoiron/sqlx"
	_ "github.com/lib/pq"
)

func main() {
	db, err := sqlx.Connect("postgres", "postgresql://postgres:postgres@localhost:5432/distcomp?sslmode=disable")
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	creatorService := service.NewCreatorService(storage.NewCreatorStorage(db))
	storyService := service.NewStoryService(storage.NewStoryStorage(db))
	markService := service.NewMarkService(storage.NewMarkStorage(db))

	e := echo.New()
	e.Use(middleware.Recover())

	creatorController := controllers.NewCreatorController(creatorService)
	storyController := controllers.NewStoryController(storyService)
	markController := controllers.NewMarkController(markService)
	// controllers.NoteControllerS(e, db);
	controllers.NewNoteController(e);

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

	e.POST("/api/v1.0/marks", markController.Create)
	e.PUT("/api/v1.0/marks", markController.Update)
	e.GET("/api/v1.0/marks", markController.GetAll)
	e.DELETE("/api/v1.0/marks/:id", markController.Delete)
	e.GET("/api/v1.0/marks/:id", markController.Get)

	e.Logger.Fatal(e.Start(":24110"))
}
