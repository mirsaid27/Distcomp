package httpapi

import (
	"context"
	"encoding/json"
	"net/http"
	"strconv"
	"time"

	"github.com/go-chi/chi/v5"
	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/di"
	"github.com/strcarne/distributed-calculations/internal/entity"
	"github.com/strcarne/distributed-calculations/internal/entity/msg"
	"github.com/strcarne/distributed-calculations/internal/entity/server"
)

const kafkaTimeout = 4 * time.Second

func NewNoteRouter(deps di.Container) *chi.Mux {
	router := chi.NewRouter()
	router.Route("/", func(r chi.Router) {
		r.Get("/", wrapHandler(GetNotes, deps))
		r.Get("/{id}", wrapHandler(GetNote, deps))
		r.Post("/", wrapHandler(CreateNote, deps))
		r.Put("/", wrapHandler(UpdateNote, deps))
		r.Delete("/{id}", wrapHandler(DeleteNote, deps))
	})

	return router
}

func GetNote(w http.ResponseWriter, r *http.Request, deps di.Container) *server.Error {
	idStr := chi.URLParam(r, "id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return server.NewError(err, http.StatusBadRequest)
	}

	ctx, cancel := context.WithTimeout(r.Context(), kafkaTimeout)
	defer cancel()

	correlationID, err := deps.Bus.PostNoteRequest(ctx, msg.NewNoteRequest(msg.MethodGet, entity.Note{ID: id}))
	if err != nil {
		return server.NewError(err, http.StatusInternalServerError)
	}

	response, err := deps.Bus.GetNoteResponse(ctx, correlationID)
	if err != nil {
		return server.NewError(err, http.StatusInternalServerError)
	}

	json.NewEncoder(w).Encode(response.Note)
	deps.Logger.Info("note retrieved", "note_id", id)

	return nil
}

func GetNotes(w http.ResponseWriter, r *http.Request, deps di.Container) *server.Error {
	ctx, cancel := context.WithTimeout(context.Background(), kafkaTimeout)
	defer cancel()

	correlationID, err := deps.Bus.PostNoteRequest(ctx, msg.NewNoteRequest(msg.MethodGetAll, entity.Note{}))
	if err != nil {
		return server.NewError(err, http.StatusInternalServerError)
	}

	deps.Logger.Info("Sent notes request", "correlation_id", correlationID)

	response, err := deps.Bus.GetNoteResponse(ctx, correlationID)
	if err != nil {
		return server.NewError(err, http.StatusInternalServerError)
	}

	deps.Logger.Info("Received notes response", "correlation_id", correlationID)

	if response.Notes == nil {
		response.Notes = []entity.Note{}
	}

	json.NewEncoder(w).Encode(response.Notes)
	deps.Logger.Info("notes retrieved", "count", len(response.Notes))

	return nil
}

func CreateNote(w http.ResponseWriter, r *http.Request, deps di.Container) *server.Error {
	var note entity.Note
	if err := json.NewDecoder(r.Body).Decode(&note); err != nil {
		return server.NewError(err, http.StatusForbidden)
	}

	ctx, cancel := context.WithTimeout(r.Context(), kafkaTimeout)
	defer cancel()

	correlationID, err := deps.Bus.PostNoteRequest(ctx, msg.NewNoteRequest(msg.MethodCreate, note))
	if err != nil {
		return server.NewError(err, http.StatusInternalServerError)
	}

	response, err := deps.Bus.GetNoteResponse(ctx, correlationID)
	if err != nil {
		return server.NewError(err, http.StatusInternalServerError)
	}

	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(response.Note)
	deps.Logger.Info("note created", "note_id", response.Note.ID)

	return nil
}

func UpdateNote(w http.ResponseWriter, r *http.Request, deps di.Container) *server.Error {
	var note entity.Note
	if err := json.NewDecoder(r.Body).Decode(&note); err != nil {
		return server.NewError(err, http.StatusForbidden)
	}

	ctx, cancel := context.WithTimeout(r.Context(), kafkaTimeout)
	defer cancel()

	correlationID, err := deps.Bus.PostNoteRequest(ctx, msg.NewNoteRequest(msg.MethodUpdate, note))
	if err != nil {
		return server.NewError(err, http.StatusInternalServerError)
	}

	response, err := deps.Bus.GetNoteResponse(ctx, correlationID)
	if err != nil {
		return server.NewError(err, http.StatusInternalServerError)
	}

	json.NewEncoder(w).Encode(response.Note)
	deps.Logger.Info("note updated", "note_id", response.Note.ID)

	return nil
}

func DeleteNote(w http.ResponseWriter, r *http.Request, deps di.Container) *server.Error {
	idStr := chi.URLParam(r, "id")
	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		return server.NewError(err, http.StatusBadRequest)
	}

	ctx, cancel := context.WithTimeout(r.Context(), kafkaTimeout)
	defer cancel()

	correlationID, err := deps.Bus.PostNoteRequest(ctx, msg.NewNoteRequest(msg.MethodDelete, entity.Note{ID: id}))
	if err != nil {
		return server.NewError(err, http.StatusInternalServerError)
	}

	response, err := deps.Bus.GetNoteResponse(ctx, correlationID)
	if err != nil {
		return server.NewError(err, http.StatusInternalServerError)
	}

	json.NewEncoder(w).Encode(response.Note)
	deps.Logger.Info("note deleted", "note_id", response.Note.ID)

	return nil
}
