package controllers

import (
	"fmt"
	"github.com/labstack/echo/v4"
	"distributedcomputing/model"
	"distributedcomputing/service"
	"net/http"
	"strconv"
)

type CreatorController struct {
	service *service.CreatorService
}

func NewCreatorController(service *service.CreatorService) *CreatorController {
	return &CreatorController{service: service}
}

func (cc *CreatorController) Create(c echo.Context) error {
	var dto model.CreatorRequestTo
	if err := c.Bind(&dto); err != nil {
		return c.JSON(http.StatusBadRequest, err.Error())
	}

	entity, err := cc.service.Create(dto)
	if err != nil {
		return c.JSON(http.StatusInternalServerError, err.Error())
	}
	return c.JSON(http.StatusCreated, entity)
}

func (cc *CreatorController) Get(c echo.Context) error {
	idStr := c.Param("id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return c.JSON(http.StatusBadRequest, "Invalid ID format")
	}

	creator, err := cc.service.Get(id)
	if err != nil {
		return c.JSON(http.StatusNotFound, err.Error())
	}

	return c.JSON(http.StatusOK, creator)
}

func (cc *CreatorController) Update(c echo.Context) error {
	var dto model.CreatorRequestTo
	if err := c.Bind(&dto); err != nil {
		return c.JSON(http.StatusBadRequest, err.Error())
	}
	fmt.Println(dto)
	if err := cc.service.Update(dto); err != nil {
		fmt.Println(err)
		return c.JSON(http.StatusInternalServerError, err.Error())
	}
	
	return c.NoContent(http.StatusOK)
}

func (cc *CreatorController) Delete(c echo.Context) error {
	idStr := c.Param("id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return c.JSON(http.StatusBadRequest, "Invalid ID format")
	}

	if err := cc.service.Delete(id); err != nil {
		return c.NoContent(http.StatusNotFound)
	}

	return c.NoContent(http.StatusNoContent)
}