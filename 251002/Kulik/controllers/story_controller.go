package controllers

import (

	"github.com/labstack/echo/v4"
	"distributedcomputing/model"
	"distributedcomputing/service"
	"net/http"
	"strconv"
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
		return c.JSON(http.StatusBadRequest, err.Error())
	}

	entity, err := sc.service.Create(dto)
	if err != nil {
		return c.JSON(http.StatusInternalServerError, err.Error())
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
		return c.JSON(http.StatusNotFound, err.Error())
	}

	return c.JSON(http.StatusOK, story)
}

func (sc *StoryController) Update(c echo.Context) error {
	var dto model.StoryRequestTo
	if err := c.Bind(&dto); err != nil {
		return c.JSON(http.StatusBadRequest, err.Error())
	}

	if err := sc.service.Update(dto); err != nil {
		return c.JSON(http.StatusInternalServerError, err.Error())
	}

	return c.NoContent(http.StatusOK)
}

func (sc *StoryController) Delete(c echo.Context) error {
	idStr := c.Param("id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return c.JSON(http.StatusBadRequest, "Invalid ID format")
	}

	if err := sc.service.Delete(id); err != nil {
		return c.JSON(http.StatusInternalServerError, err.Error())
	}

	return c.NoContent(http.StatusNoContent)
}
