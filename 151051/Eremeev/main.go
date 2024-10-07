package main

import (
	"DC-eremeev/app"
	"DC-eremeev/config"
	"DC-eremeev/db"
	"flag"
	"log"
)

func main() {
	dropSchema := flag.Bool("drop", false, "drop schema on start")
	flag.Parse()

	_, err := config.LoadConfig()
	if err != nil {
		log.Fatalln("Can not load config:", err.Error())
	}

	db.Init(*dropSchema)

	app.Run()
}
