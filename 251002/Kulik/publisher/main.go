package main

import (
	// "fmt"
	"log"
	"net/http/httputil"
	"net/url"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"

	"distributedcomputing/controllers"
	"distributedcomputing/service"
	"distributedcomputing/storage"

	"github.com/jmoiron/sqlx"
	_ "github.com/lib/pq"
)

func reverseProxy(target string) echo.HandlerFunc {
	targetURL, err := url.Parse(target)
	if err != nil {
		log.Fatalf("Invalid proxy target URL: %v", err)
	}

	proxy := httputil.NewSingleHostReverseProxy(targetURL)

	return func(c echo.Context) error {
		c.Request().URL.Host = targetURL.Host
		c.Request().URL.Scheme = targetURL.Scheme
		c.Request().Host = targetURL.Host
		proxy.ServeHTTP(c.Response(), c.Request())
		return nil
	}
}

func main() {
	// creatorRepo := storage.NewInMemStorage[model.Creator]()
	// storyRepo := storage.NewInMemStorage[model.Story]()
	// noteRepo := storage.NewInMemStorage[model.Note]()
	// markRepo := storage.NewInMemStorage[model.Mark]()

	db, err := sqlx.Connect("postgres", "postgresql://postgres:postgres@localhost:5432/distcomp?sslmode=disable")
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	
	creatorRepo := storage.NewCreatorStorage(db)
	storyRepo := storage.NewStoryStorage(db)
	// noteRepo := storage.NewNoteStorage(db)
	markRepo := storage.NewMarkStorage(db)

	creatorService := service.NewCreatorService(creatorRepo)
	storyService := service.NewStoryService(storyRepo)
	// noteService := service.NewNoteService(noteRepo)
	markService := service.NewMarkService(markRepo)

	creatorController := controllers.NewCreatorController(creatorService)
	storyController := controllers.NewStoryController(storyService)
	// noteController := controllers.NewNoteController(noteService)
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

	targetIP := "http://localhost:24130"
	e.Any("/api/v1.0/notes/*", reverseProxy(targetIP))
	e.Any("/api/v1.0/notes", reverseProxy(targetIP))  

	e.POST("/api/v1.0/marks", markController.Create)
	e.PUT("/api/v1.0/marks", markController.Update)
	e.GET("/api/v1.0/marks", markController.GetAll)
	e.DELETE("/api/v1.0/marks/:id", markController.Delete)
	e.GET("/api/v1.0/marks/:id", markController.Get)

	e.Logger.Fatal(e.Start(":24110"))
}
