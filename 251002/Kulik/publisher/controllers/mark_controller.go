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

type MarkController struct {
	service *service.MarkService
}

func NewMarkController(service *service.MarkService) *MarkController {
	return &MarkController{service: service}
}

func (mc *MarkController) Create(c echo.Context) error {
	var dto model.MarkRequestTo
	if err := c.Bind(&dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}
	
	if err := validateMark(dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	mark, err := mc.service.Create(dto)
	if err != nil {
		return c.JSON(http.StatusForbidden, WrapErr(err))
	}

	fmt.Println(mark)
	return c.JSON(http.StatusCreated, mark)
}

func (mc *MarkController) Get(c echo.Context) error {
	idStr := c.Param("id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return c.JSON(http.StatusBadRequest, "Invalid ID format")
	}

	mark, err := mc.service.Get(id)
	if err != nil {
		return c.JSON(http.StatusNotFound, WrapErr(err))
	}

	return c.JSON(http.StatusOK, mark)
}

func (mc *MarkController) GetAll(c echo.Context) error {
	marks, err := mc.service.GetAll()
	if err != nil {
		return c.JSON(http.StatusNotFound, WrapErr(err))
	}
	return c.JSON(http.StatusOK, marks)
}

func (mc *MarkController) Update(c echo.Context) error {
	var dto model.MarkRequestTo
	if err := c.Bind(&dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	if err := validateMark(dto); err != nil {
		return c.JSON(http.StatusBadRequest, WrapErr(err))
	}

	if err := mc.service.Update(dto); err != nil {
		fmt.Println("lala", err)
		return c.JSON(http.StatusForbidden, WrapErr(err))
	}

	return c.JSON(http.StatusOK, dto)
}

func (mc *MarkController) Delete(c echo.Context) error {
	idStr := c.Param("id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return c.NoContent(http.StatusNoContent)
	}

	if err := mc.service.Delete(id); err != nil {
		return c.NoContent(http.StatusNotFound)
	}

	return c.NoContent(http.StatusNoContent)
}



func validateMark(dto model.MarkRequestTo) error {
	if len(dto.Name) < 2 || len(dto.Name) > 32 {
		return errors.New("mark name must be between 2 and 32 characters")
	}
	return nil
}
