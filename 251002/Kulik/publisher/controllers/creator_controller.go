package controllers

import (
	"fmt"
	"github.com/labstack/echo/v4"
	"distributedcomputing/model"
	"distributedcomputing/service"
	"net/http"
	"strconv"
	"errors"
)


func WrapErr(err error) map[string]string {
	return map[string]string{"error": err.Error()}
}

type CreatorController struct {
	service *service.CreatorService
}

func NewCreatorController(service *service.CreatorService) *CreatorController {
	return &CreatorController{service: service}
}

func (cc *CreatorController) Create(c echo.Context) error {
	var dto model.CreatorRequestTo
	if err := c.Bind(&dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	fmt.Println("bbbbb: ", dto)

	if err := validateCreator(dto); err != nil {
		fmt.Println(err)
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	entity, err := cc.service.Create(dto)
	if err != nil {
		fmt.Println(err)
		return c.JSON(http.StatusForbidden, WrapErr(err))
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
		return c.JSON(http.StatusNotFound, WrapErr(err))
	}

	return c.JSON(http.StatusOK, creator)
}

func (cc *CreatorController) GetAll(c echo.Context) error {
	creators, err := cc.service.GetAll()
	if err != nil {
		return c.JSON(http.StatusNotFound, WrapErr(err))
	}
	return c.JSON(http.StatusOK, creators)
}


func (cc *CreatorController) Update(c echo.Context) error {
	var dto model.CreatorRequestTo
	if err := c.Bind(&dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	if err := validateCreator(dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	if err := cc.service.Update(dto); err != nil {
		fmt.Println(err)
		return c.JSON(http.StatusInternalServerError, WrapErr(err))
	}
	
	return c.JSON(http.StatusOK, dto)
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

func validateCreator(dto model.CreatorRequestTo) error {
	if len(dto.Login) < 2 || len(dto.Login) > 64 {
		return errors.New("login must be between 2 and 64 characters")
	}
	if len(dto.Password) < 8 || len(dto.Password) > 128 {
		return errors.New("password must be between 8 and 128 characters")
	}
	if len(dto.FirstName) < 2 || len(dto.FirstName) > 64 {
		return errors.New("firstname must be between 2 and 64 characters")
	}
	if len(dto.LastName) < 2 || len(dto.LastName) > 64 {
		return errors.New("lastname must be between 2 and 64 characters")
	}
	return nil
}