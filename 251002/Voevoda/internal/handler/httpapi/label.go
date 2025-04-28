package handler

import (
	"encoding/json"
	"net/http"
	"strconv"

	"github.com/go-chi/chi/v5"
	"github.com/strcarne/task310-rest/internal/entity"
	"github.com/strcarne/task310-rest/internal/repository/inmemory"
	"github.com/strcarne/task310-rest/internal/service"
)

var labelService = service.NewLabel(inmemory.NewLabelRepository())

func NewLabelRouter() *chi.Mux {
	r := chi.NewRouter()

	r.Route("/", func(r chi.Router) {
		r.Get("/", GetLabels)
		r.Get("/{id}", GetLabel)
		r.Post("/", CreateLabel)
		r.Delete("/{id}", DeleteLabel)
		r.Put("/", UpdateLabel)
	})

	return r
}

func GetLabels(w http.ResponseWriter, r *http.Request) {
	labels := labelService.GetAllLabels()
	json.NewEncoder(w).Encode(labels)
}

func GetLabel(w http.ResponseWriter, r *http.Request) {
	idStr := chi.URLParam(r, "id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	label, err := labelService.GetLabelByID(id)
	if err != nil {
		http.Error(w, err.Error(), http.StatusNotFound)
		return
	}

	json.NewEncoder(w).Encode(label)
}

func CreateLabel(w http.ResponseWriter, r *http.Request) {
	var label entity.Label
	if err := json.NewDecoder(r.Body).Decode(&label); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	label = labelService.CreateLabel(label)
	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(label)
}

func DeleteLabel(w http.ResponseWriter, r *http.Request) {
	idStr := chi.URLParam(r, "id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	if label, err := labelService.DeleteLabel(id); err != nil {
		w.WriteHeader(http.StatusNotFound)
		json.NewEncoder(w).Encode(label)
		return
	}

	w.WriteHeader(http.StatusNoContent)
}

func UpdateLabel(w http.ResponseWriter, r *http.Request) {
	var label entity.Label
	if err := json.NewDecoder(r.Body).Decode(&label); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	if err := labelService.UpdateLabel(label); err != nil {
		w.WriteHeader(http.StatusNotFound)
		json.NewEncoder(w).Encode(label)
		return
	}

	w.WriteHeader(http.StatusOK)
	json.NewEncoder(w).Encode(label)
}
