package main

import (
	"context"
	"log"

	"github.com/Khmelov/Distcomp/251003/Nasevich/restbasics/internal/app"

	"github.com/joho/godotenv"
)

func init() {
	if err := godotenv.Load(); err != nil {
		log.Fatalf("couldn't read .env file: %v", err)
	}
}

func main() {
	app := app.New()

	if err := app.Start(context.Background()); err != nil {
		log.Fatalf("couldn't start server")
	}
}
