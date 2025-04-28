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

type noteHandlerFunc func(w http.ResponseWriter, r *http.Request, noteService service.Note)

func NewNoteRouter(queries *generated.Queries) *chi.Mux {
	r := chi.NewRouter()

	noteRepository := psql.NewNoteRepository(queries)
	noteService := service.NewNote(noteRepository)

	r.Route("/", func(r chi.Router) {
		r.Get("/", wrapNoteHandler(GetNotes, noteService))
		r.Get("/{id}", wrapNoteHandler(GetNote, noteService))
		r.Post("/", wrapNoteHandler(CreateNote, noteService))
		r.Delete("/{id}", wrapNoteHandler(DeleteNote, noteService))
		r.Put("/", wrapNoteHandler(UpdateNote, noteService))
	})

	return r
}

func wrapNoteHandler(handler noteHandlerFunc, noteService service.Note) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		handler(w, r, noteService)
	}
}

func GetNotes(w http.ResponseWriter, r *http.Request, noteService service.Note) {
	notes, err := noteService.GetAllNotes()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	json.NewEncoder(w).Encode(notes)
}

func GetNote(w http.ResponseWriter, r *http.Request, noteService service.Note) {
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

func CreateNote(w http.ResponseWriter, r *http.Request, noteService service.Note) {
	var note entity.Note
	if err := json.NewDecoder(r.Body).Decode(&note); err != nil {
		w.WriteHeader(http.StatusForbidden)
		json.NewEncoder(w).Encode(note)
		return
	}

	note, err := noteService.CreateNote(note)
	if err != nil {
		w.WriteHeader(http.StatusForbidden)
		json.NewEncoder(w).Encode(note)
		return
	}

	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(note)
}

func DeleteNote(w http.ResponseWriter, r *http.Request, noteService service.Note) {
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

func UpdateNote(w http.ResponseWriter, r *http.Request, noteService service.Note) {
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
