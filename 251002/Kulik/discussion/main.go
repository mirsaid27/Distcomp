package main

import (
	"log"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"

	"distributedcomputing/controllers"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"distributedcomputing/service"
	"distributedcomputing/storage"
	_ "github.com/lib/pq"
	"context"
)

func main() {
	clientOptions := options.Client().ApplyURI("mongodb://localhost:27017")
	client, err := mongo.Connect(context.TODO(), clientOptions)
	if err != nil {
		log.Fatal(err)
	}

	defer client.Disconnect(context.TODO())

	dbName := "notesDB"
	collectionName := "notes"

	storage := storage.NewNoteStorage(client, dbName, collectionName)
	noteService := service.NewNoteService(storage)
	noteController := controllers.NewNoteController(noteService)

	e := echo.New()
	e.Use(middleware.Recover())

	e.POST("/api/v1.0/notes", noteController.Create)
	e.PUT("/api/v1.0/notes", noteController.Update)
	e.GET("/api/v1.0/notes", noteController.GetAll)
	e.DELETE("/api/v1.0/notes/:id", noteController.Delete)
	e.GET("/api/v1.0/notes/:id", noteController.Get)

	e.Logger.Fatal(e.Start(":24130"))
}
