package main

import (
	"context"
	"distributedcomputing/controllers"
	"distributedcomputing/service"
	"distributedcomputing/storage"
	"encoding/json"

	// "fmt"
	"log"
	"strconv"
	"time"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	"github.com/segmentio/kafka-go"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

// --------- Kafka Topics & Config ---------
const (
	kafkaBroker = "localhost:9094"
	inTopic     = "InTopic"
	outTopic    = "OutTopic"
)

// --------- Mongo Config ---------
const (
	mongoURI        = "mongodb://localhost:27017"
	databaseName    = "notes_db"
	collectionName  = "notes"
	mongoTimeoutSec = 10
)

type Note struct {
	Id      int64  `json:"id" bson:"id"`
	Title   string `json:"title" bson:"title"`
	Content string `json:"content" bson:"content"`
}

type NoteMessage struct {
	Action string `json:"action"`
	ID     string `json:"id,omitempty"`
	Data   string `json:"data,omitempty"`
}

func main() {

	clientOptions := options.Client().ApplyURI("mongodb://localhost:27017")
	client, err := mongo.Connect(context.TODO(), clientOptions)
	if err != nil {
		log.Fatal(err)
	}

	defer client.Disconnect(context.TODO())

	ctx := context.Background()

	// Connect to MongoDB
	
	ctxMongo, cancel := context.WithTimeout(ctx, mongoTimeoutSec*time.Second)
	defer cancel()

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

	if err := client.Connect(ctxMongo); err != nil {
		log.Fatalf("Mongo connection failed: %v", err)
	}
	defer client.Disconnect(ctxMongo)

	collection := client.Database(databaseName).Collection(collectionName)

	// Kafka Consumer
	reader := kafka.NewReader(kafka.ReaderConfig{
		Brokers:  []string{kafkaBroker},
		GroupID:  "note-service-group",
		Topic:    inTopic,
		MinBytes: 10e3,
		MaxBytes: 10e6,
	})
	defer reader.Close()

	// Kafka Producer
	writer := &kafka.Writer{
		Addr:     kafka.TCP(kafkaBroker),
		Topic:    outTopic,
		Balancer: &kafka.LeastBytes{},
	}
	defer writer.Close()

	log.Println("Note Service is listening for Kafka messages...")

	for {
		m, err := reader.ReadMessage(ctx)
		if err != nil {
			log.Printf("Kafka read error: %v", err)
			continue
		}

		var msg NoteMessage
		if err := json.Unmarshal(m.Value, &msg); err != nil {
			log.Printf("Unmarshal error: %v", err)
			continue
		}

		log.Printf("Received message: %+v", msg)

		var response interface{}
		switch msg.Action {
		case "create":
			var note Note
			if err := json.Unmarshal([]byte(msg.Data), &note); err != nil {
				response = errorMsg("Invalid create data")
				break
			}
			_, err := collection.InsertOne(ctx, note)
			if err != nil {
				response = errorMsg("Mongo insert failed: " + err.Error())
			} else {
				response = note
			}

		case "update":
			var note Note
			if err := json.Unmarshal([]byte(msg.Data), &note); err != nil {
				response = errorMsg("Invalid update data")
				break
			}
			filter := bson.M{"id": note.Id}
			update := bson.M{"$set": bson.M{"title": note.Title, "content": note.Content}}
			_, err := collection.UpdateOne(ctx, filter, update)
			if err != nil {
				response = errorMsg("Mongo update failed: " + err.Error())
			} else {
				response = note
			}

		case "delete":
			id, _ := strconv.ParseInt(msg.ID, 10, 64)
			_, err := collection.DeleteOne(ctx, bson.M{"id": id})
			if err != nil {
				response = errorMsg("Mongo delete failed: " + err.Error())
			} else {
				response = map[string]string{"status": "deleted"}
			}

		case "get":
			id, _ := strconv.ParseInt(msg.ID, 10, 64)
			var note Note
			err := collection.FindOne(ctx, bson.M{"id": id}).Decode(&note)
			if err != nil {
				response = errorMsg("Note not found")
			} else {
				response = note
			}

		case "get_all":
			cur, err := collection.Find(ctx, bson.M{})
			if err != nil {
				response = errorMsg("Mongo find failed")
				break
			}
			defer cur.Close(ctx)

			var notes []Note
			for cur.Next(ctx) {
				var note Note
				if err := cur.Decode(&note); err == nil {
					notes = append(notes, note)
				}
			}
			response = notes

		default:
			response = errorMsg("Unknown action")
		}

		respBytes, _ := json.Marshal(NoteMessage{
			ID:   msg.ID,
			Data: string(mustJSON(response)),
		})
		err = writer.WriteMessages(ctx, kafka.Message{Key: []byte(msg.ID), Value: respBytes})
		if err != nil {
			log.Printf("Failed to write response: %v", err)
		}
	}
}

func errorMsg(msg string) map[string]string {
	return map[string]string{"error": msg}
}

func mustJSON(v interface{}) []byte {
	b, _ := json.Marshal(v)
	return b
}
