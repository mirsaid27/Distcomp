package controllers

import (
	"distributedcomputing/model"
	"distributedcomputing/service"
	"distributedcomputing/storage"
	"errors"
	"fmt"
	"net/http"
	"strconv"

	"github.com/jmoiron/sqlx"
	"github.com/labstack/echo/v4"
)

type StoryController struct {
	service *service.StoryService
}

func NewStoryController(service *service.StoryService) *StoryController {
	return &StoryController{service: service}
}

func (sc *StoryController) Create(c echo.Context) error {
	var dto model.StoryRequestTo
	if err := c.Bind(&dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	if err := validateStory(dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	entity, err := sc.service.Create(dto)
	if err != nil {
		return c.JSON(http.StatusForbidden, WrapErr(err))
	}

	return c.JSON(http.StatusCreated, entity)
}

func (sc *StoryController) Get(c echo.Context) error {
	idStr := c.Param("id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return c.JSON(http.StatusBadRequest, "Invalid ID format")
	}

	story, err := sc.service.Get(id)
	if err != nil {
		return c.JSON(http.StatusNotFound, WrapErr(err))
	}

	return c.JSON(http.StatusOK, story)
}

func (sc *StoryController) Update(c echo.Context) error {
	var dto model.StoryRequestTo
	if err := c.Bind(&dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	fmt.Println("lalalal: ", dto);
	if err := validateStory(dto); err != nil {
		fmt.Println(err)
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	if err := sc.service.Update(dto); err != nil {
		fmt.Println(err)
		return c.JSON(http.StatusInternalServerError, WrapErr(err))
	}

	return c.JSON(http.StatusOK, dto)
}

func (sc *StoryController) Delete(c echo.Context) error {
	idStr := c.Param("id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return c.JSON(http.StatusBadRequest, "Invalid ID format")
	}

	if err := sc.service.Delete(id); err != nil {
		return c.NoContent(http.StatusNotFound)
	}

	return c.NoContent(http.StatusNoContent)
}

func (cc *StoryController) GetAll(c echo.Context) error {
	creators, err := cc.service.GetAll()
	if err != nil {
		return c.JSON(http.StatusNotFound, WrapErr(err))
	}
	return c.JSON(http.StatusOK, creators)
}

func validateStory(dto model.StoryRequestTo) error {
	if len(dto.Title) < 2 || len(dto.Title) > 64 {
		return errors.New("title must be between 2 and 64 characters")
	}
	if len(dto.Content) < 4 || len(dto.Content) > 2048 {
		return errors.New("content must be between 4 and 2048 characters")
	}
	return nil
}


func NoteControllerS(e *echo.Echo, db *sqlx.DB) {
	noteRepo := storage.NewNoteStorage(db)
	noteService := service.NewNoteService(noteRepo)
	noteController := NewNoteC(noteService)
	e.POST("/api/v1.0/notes", noteController.Create)
	e.PUT("/api/v1.0/notes", noteController.Update)
	e.GET("/api/v1.0/notes", noteController.GetAll)
	e.DELETE("/api/v1.0/notes/:id", noteController.Delete)
	e.GET("/api/v1.0/notes/:id", noteController.Get)
}

type NoteC struct {
	service *service.NoteService
}


func NewNoteC(service *service.NoteService) *NoteC {
	return &NoteC{service: service}
}

func (nc *NoteC) Create(c echo.Context) error {
	var dto model.NoteRequestTo
	if err := c.Bind(&dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	if err := validateNote(dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	note, err := nc.service.Create(dto)
	if err != nil {
		return c.JSON(http.StatusForbidden, WrapErr(err))
	}
	return c.JSON(http.StatusCreated, note)
}

func (nc *NoteC) Get(c echo.Context) error {
	idStr := c.Param("id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return c.JSON(http.StatusBadRequest, "Invalid ID format")
	}

	note, err := nc.service.Get(id)
	if err != nil {
		return c.JSON(http.StatusNotFound, WrapErr(err))
	}

	return c.JSON(http.StatusOK, note)
}

func (nc *NoteC) GetAll(c echo.Context) error {
	notes, err := nc.service.GetAll()
	if err != nil {
		return c.JSON(http.StatusNotFound, WrapErr(err))
	}
	return c.JSON(http.StatusOK, notes)
}

func (nc *NoteC) Update(c echo.Context) error {
	var dto model.NoteRequestTo
	if err := c.Bind(&dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	if err := validateNote(dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	if err := nc.service.Update(dto); err != nil {
		fmt.Println(err)
		return c.JSON(http.StatusForbidden, WrapErr(err))
	}

	return c.JSON(http.StatusOK, dto)
}

func (nc *NoteC) Delete(c echo.Context) error {
	idStr := c.Param("id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return c.JSON(http.StatusBadRequest, "Invalid ID format")
	}

	if err := nc.service.Delete(id); err != nil {
		return c.NoContent(http.StatusNotFound)
	}

	return c.NoContent(http.StatusNoContent)
}




func validateNote(dto model.NoteRequestTo) error {
	if len(dto.Content) < 2 || len(dto.Content) > 2048 {
		return errors.New("note content must be between 2 and 2048 characters")
	}
	return nil
}