package main

import (
	"encoding/json"
	"log"
	// "net/url"
	// "os"
	"strconv"
	// "time"

	"github.com/IBM/sarama"
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"

	"distributedcomputing/controllers"
	"distributedcomputing/service"
	"distributedcomputing/model"
	"distributedcomputing/storage"

	"github.com/jmoiron/sqlx"
	_ "github.com/lib/pq"
)

const (
	kafkaBroker = "localhost:9092"
	inTopic     = "InTopic"
	outTopic    = "OutTopic"
)

type KafkaMessage struct {
	Action  string `json:"action"`
	StoryID int    `json:"story_id"`
	Message string `json:"message"`
}

type KafkaProducer struct {
	producer sarama.SyncProducer
}

func NewKafkaProducer() *KafkaProducer {
	config := sarama.NewConfig()
	config.Producer.RequiredAcks = sarama.WaitForAll
	config.Producer.Return.Successes = true

	producer, err := sarama.NewSyncProducer([]string{kafkaBroker}, config)
	if err != nil {
		log.Fatalf("Failed to start Kafka producer: %v", err)
	}
	return &KafkaProducer{producer: producer}
}

func (kp *KafkaProducer) SendMessage(action string, storyID int, message string) error {
	partitionKey := strconv.Itoa(storyID)
	msg := KafkaMessage{Action: action, StoryID: storyID, Message: message}
	encodedMsg, _ := json.Marshal(msg)

	kafkaMsg := &sarama.ProducerMessage{
		Topic: inTopic,
		Key:   sarama.StringEncoder(partitionKey),
		Value: sarama.StringEncoder(encodedMsg),
	}

	_, _, err := kp.producer.SendMessage(kafkaMsg)
	return err
}

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

func (kc *KafkaConsumer) ConsumeMessages() {
	partitionConsumer, err := kc.consumer.ConsumePartition(outTopic, 0, sarama.OffsetNewest)
	if err != nil {
		log.Fatalf("Failed to start Kafka partition consumer: %v", err)
	}
	defer partitionConsumer.Close()

	for msg := range partitionConsumer.Messages() {
		log.Printf("Received message: %s", string(msg.Value))
	}
}

func main() {
	db, err := sqlx.Connect("postgres", "postgresql://postgres:postgres@localhost:5432/distcomp?sslmode=disable")
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	creatorRepo := storage.NewCreatorStorage(db)
	storyRepo := storage.NewStoryStorage(db)
	markRepo := storage.NewMarkStorage(db)

	creatorService := service.NewCreatorService(creatorRepo)
	storyService := service.NewStoryService(storyRepo)
	markService := service.NewMarkService(markRepo)

	creatorController := controllers.NewCreatorController(creatorService)
	storyController := controllers.NewStoryController(storyService)
	markController := controllers.NewMarkController(markService)

	kafkaProducer := NewKafkaProducer()
	go NewKafkaConsumer().ConsumeMessages()

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

	e.POST("/api/v1.0/notes", func(c echo.Context) error {
		noteRequest := new(model.NoteRequestTo)
		if err := c.Bind(noteRequest); err != nil {
			return c.JSON(400, map[string]string{"error": "Invalid request body"})
		}
		if err := kafkaProducer.SendMessage("create", int(noteRequest.StoryID), noteRequest.Content); err != nil {
			return c.JSON(500, map[string]string{"error": "Failed to send Kafka message"})
		}
		return c.JSON(200, map[string]string{"status": "Message sent"})
	})

	e.PUT("/api/v1.0/notes", func(c echo.Context) error {
		noteRequest := new(model.NoteRequestTo)
		if err := c.Bind(noteRequest); err != nil {
			return c.JSON(400, map[string]string{"error": "Invalid request body"})
		}
		if err := kafkaProducer.SendMessage("update", int(noteRequest.StoryID), noteRequest.Content); err != nil {
			return c.JSON(500, map[string]string{"error": "Failed to send Kafka message"})
		}
		return c.JSON(200, map[string]string{"status": "Message sent"})
	})

	e.DELETE("/api/v1.0/notes/:id", func(c echo.Context) error {
		storyID, _ := strconv.Atoi(c.Param("id"))
		if err := kafkaProducer.SendMessage("delete", storyID, ""); err != nil {
			return c.JSON(500, map[string]string{"error": "Failed to send Kafka message"})
		}
		return c.JSON(200, map[string]string{"status": "Message sent"})
	})

	// Define GET routes for notes
	e.GET("/api/v1.0/notes", func(c echo.Context) error {
		// Get all notes from DB or any service layer
		notes := []model.NoteResponseTo{
			{Id: 1, StoryID: 123, Content: "Note 1"},
			{Id: 2, StoryID: 124, Content: "Note 2"},
		}
		return c.JSON(200, notes)
	})

	e.GET("/api/v1.0/notes/:id", func(c echo.Context) error {
		// Get a specific note by ID
		id := c.Param("id")
		// Fetch note from DB or service layer
		note := model.NoteResponseTo{Id: 1, StoryID: 123, Content: "Note 1"}
		if id != "1" {
			return c.JSON(404, map[string]string{"error": "Note not found"})
		}
		return c.JSON(200, note)
	})

	e.POST("/api/v1.0/marks", markController.Create)
	e.PUT("/api/v1.0/marks", markController.Update)
	e.GET("/api/v1.0/marks", markController.GetAll)
	e.DELETE("/api/v1.0/marks/:id", markController.Delete)
	e.GET("/api/v1.0/marks/:id", markController.Get)

	e.Logger.Fatal(e.Start(":24110"))
}
