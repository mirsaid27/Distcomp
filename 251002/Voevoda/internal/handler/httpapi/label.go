package handler

import (
	"encoding/json"
	"net/http"
	"strconv"

	"github.com/go-chi/chi/v5"
	"github.com/strcarne/task310-rest/internal/entity"
	"github.com/strcarne/task310-rest/internal/repository/psql"
	"github.com/strcarne/task310-rest/internal/repository/psql/generated"
	"github.com/strcarne/task310-rest/internal/service"
)

type labelHandlerFunc func(w http.ResponseWriter, r *http.Request, labelService service.Label)

func NewLabelRouter(queries *generated.Queries) *chi.Mux {
	r := chi.NewRouter()

	labelRepository := psql.NewLabelRepository(queries)
	labelService := service.NewLabel(labelRepository)

	r.Route("/", func(r chi.Router) {
		r.Get("/", wrapLabelHandler(GetLabels, labelService))
		r.Get("/{id}", wrapLabelHandler(GetLabel, labelService))
		r.Post("/", wrapLabelHandler(CreateLabel, labelService))
		r.Delete("/{id}", wrapLabelHandler(DeleteLabel, labelService))
		r.Put("/", wrapLabelHandler(UpdateLabel, labelService))
	})

	return r
}

func wrapLabelHandler(handler labelHandlerFunc, labelService service.Label) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		handler(w, r, labelService)
	}
}

func GetLabels(w http.ResponseWriter, r *http.Request, labelService service.Label) {
	labels, err := labelService.GetAllLabels()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	json.NewEncoder(w).Encode(labels)
}

func GetLabel(w http.ResponseWriter, r *http.Request, labelService service.Label) {
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

func CreateLabel(w http.ResponseWriter, r *http.Request, labelService service.Label) {
	var label entity.Label
	if err := json.NewDecoder(r.Body).Decode(&label); err != nil {
		w.WriteHeader(http.StatusForbidden)
		json.NewEncoder(w).Encode(label)
		return
	}

	label, err := labelService.CreateLabel(label)
	if err != nil {
		w.WriteHeader(http.StatusForbidden)
		json.NewEncoder(w).Encode(label)
		return
	}

	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(label)
}

func DeleteLabel(w http.ResponseWriter, r *http.Request, labelService service.Label) {
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

func UpdateLabel(w http.ResponseWriter, r *http.Request, labelService service.Label) {
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
