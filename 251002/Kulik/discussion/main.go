package main

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"strconv"

	"github.com/IBM/sarama"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"

	// "distributedcomputing/controllers"
	"distributedcomputing/model"
	"distributedcomputing/service"
	"distributedcomputing/storage"
)

const (
	kafkaBroker = "localhost:9092"
	inTopic  = "InTopic"
)

type KafkaConsumer struct {
	consumer sarama.Consumer
}

func NewKafkaConsumer() *KafkaConsumer {
	consumer, err := sarama.NewConsumer([]string{kafkaBroker}, nil)
	if err != nil {
		log.Fatalf("Failed to start Kafka consumer: %v", err)
	}
	return &KafkaConsumer{consumer: consumer}
}

type NoteMessage struct {
	Action string `json:"action"`
	ID     string `json:"id,omitempty"`
	Data   string `json:"data,omitempty"`
}

func (kc *KafkaConsumer) ConsumeMessages(noteService *service.NoteService) {
	partitionConsumer, err := kc.consumer.ConsumePartition(inTopic, 0, sarama.OffsetNewest)
	if err != nil {
		log.Fatalf("Failed to start Kafka partition consumer: %v", err)
	}
	defer partitionConsumer.Close()

	fmt.Println("lalala")
	for msg := range partitionConsumer.Messages() {
		var noteMsg NoteMessage
		fmt.Println(",sg")
		if err := json.Unmarshal(msg.Value, &noteMsg); err != nil {
			log.Printf("Failed to parse message: %v", err)
			continue
		}

		fmt.Println(noteMsg, "lala")
		// Convert noteMsg.Data to model.NoteRequestTo if required
		switch noteMsg.Action {
		case "create":
			var noteRequest model.NoteRequestTo
			fmt.Println(noteMsg.Action)
			if err := json.Unmarshal([]byte(noteMsg.Data), &noteRequest); err != nil {
				log.Printf("Failed to parse note data: %v", err)
				continue
			}
			noteService.Create(noteRequest)

		case "update":
			var noteRequest model.NoteRequestTo
			if err := json.Unmarshal([]byte(noteMsg.Data), &noteRequest); err != nil {
				log.Printf("Failed to parse note data: %v", err)
				continue
			}
			noteService.Update(noteRequest)

		case "delete":
			noteID, err := strconv.ParseInt(noteMsg.ID, 10, 64)
			if err != nil {
				log.Printf("Failed to parse note ID: %v", err)
				continue
			}
			noteService.Delete(noteID)

		case "get_all":
			noteService.GetAll()

		case "get":
			noteID, err := strconv.ParseInt(noteMsg.ID, 10, 64)
			if err != nil {
				log.Printf("Failed to parse note ID: %v", err)
				continue
			}
			noteService.Get(noteID)

		default:
			log.Printf("Unknown action: %s", noteMsg.Action)
		}
	}
}

func main() {
	clientOptions := options.Client().ApplyURI("mongodb://localhost:27017")
	client, err := mongo.Connect(context.TODO(), clientOptions)
	if err != nil {
		log.Fatal(err)
	}
	defer client.Disconnect(context.TODO())

	dbName := "notesDB"
	collectionName := "notes"

	noteStorage := storage.NewNoteStorage(client, dbName, collectionName)
	noteService := service.NewNoteService(noteStorage)

	consumer := NewKafkaConsumer()
	go consumer.ConsumeMessages(noteService)

	select {} // Keep main running
}
