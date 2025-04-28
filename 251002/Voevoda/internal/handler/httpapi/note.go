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

var noteService = service.NewNote(inmemory.NewNoteRepository())

func NewNoteRouter() *chi.Mux {
	r := chi.NewRouter()

	r.Route("/", func(r chi.Router) {
		r.Get("/", GetNotes)
		r.Get("/{id}", GetNote)
		r.Post("/", CreateNote)
		r.Delete("/{id}", DeleteNote)
		r.Put("/", UpdateNote)
	})

	return r
}

func GetNotes(w http.ResponseWriter, r *http.Request) {
	notes := noteService.GetAllNotes()
	json.NewEncoder(w).Encode(notes)
}

func GetNote(w http.ResponseWriter, r *http.Request) {
	idStr := chi.URLParam(r, "id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	note, err := noteService.GetNoteByID(id)
	if err != nil {
		http.Error(w, err.Error(), http.StatusNotFound)
		return
	}

	json.NewEncoder(w).Encode(note)
}

func CreateNote(w http.ResponseWriter, r *http.Request) {
	var note entity.Note
	if err := json.NewDecoder(r.Body).Decode(&note); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	note = noteService.CreateNote(note)
	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(note)
}

func DeleteNote(w http.ResponseWriter, r *http.Request) {
	idStr := chi.URLParam(r, "id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	if note, err := noteService.DeleteNote(id); err != nil {
		w.WriteHeader(http.StatusNotFound)
		json.NewEncoder(w).Encode(note)
		return
	}

	w.WriteHeader(http.StatusNoContent)
}

func UpdateNote(w http.ResponseWriter, r *http.Request) {
	var note entity.Note
	if err := json.NewDecoder(r.Body).Decode(&note); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	if err := noteService.UpdateNote(note); err != nil {
		w.WriteHeader(http.StatusNotFound)
		json.NewEncoder(w).Encode(note)
		return
	}

	w.WriteHeader(http.StatusOK)
	json.NewEncoder(w).Encode(note)
}
