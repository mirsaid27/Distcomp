package controllers

import (
	"distributedcomputing/model"
	"distributedcomputing/service"
	"errors"
	"fmt"
	"net/http"
	"strconv"

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
