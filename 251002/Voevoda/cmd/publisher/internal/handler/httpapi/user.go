package httpapi

import (
	"encoding/json"
	"errors"
	"net/http"
	"strconv"

	"github.com/go-chi/chi/v5"
	"github.com/jackc/pgx/v5/pgconn"

	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/repository/psql"
	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/repository/psql/generated"
	"github.com/strcarne/distributed-calculations/cmd/publisher/internal/service"
	"github.com/strcarne/distributed-calculations/internal/entity"
)

type userHandlerFunc func(w http.ResponseWriter, r *http.Request, userService service.User)

func NewUserRouter(queries *generated.Queries) *chi.Mux {
	r := chi.NewRouter()

	userRepository := psql.NewUserRepository(queries)
	userService := service.NewUser(userRepository)

	r.Route("/", func(r chi.Router) {
		r.Get("/", wrapUserHandler(GetUsers, userService))
		r.Get("/{id}", wrapUserHandler(GetUser, userService))
		r.Post("/", wrapUserHandler(CreateUser, userService))
		r.Delete("/{id}", wrapUserHandler(DeleteUser, userService))
		r.Put("/", wrapUserHandler(UpdateUser, userService))
	})

	return r
}

func wrapUserHandler(handler userHandlerFunc, userService service.User) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		handler(w, r, userService)
	}
}

func GetUsers(w http.ResponseWriter, r *http.Request, userService service.User) {
	users, err := userService.GetAllUsers()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)

		return
	}

	json.NewEncoder(w).Encode(users)
}

func GetUser(w http.ResponseWriter, r *http.Request, userService service.User) {
	idStr := chi.URLParam(r, "id")

	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)

		return
	}

	user, err := userService.GetUserByID(id)
	if err != nil {
		http.Error(w, err.Error(), http.StatusNotFound)

		return
	}

	json.NewEncoder(w).Encode(user)
}

func CreateUser(w http.ResponseWriter, r *http.Request, userService service.User) {
	var user entity.User
	if err := json.NewDecoder(r.Body).Decode(&user); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)

		return
	}

	user, err := userService.CreateUser(user)
	if err != nil {
		pgErr := &pgconn.PgError{}
		if errors.As(err, &pgErr) &&
			pgErr.Code == "23505" { // Duplicate key value violates unique constraint.
			w.WriteHeader(http.StatusForbidden)
		} else {
			w.WriteHeader(http.StatusBadRequest)
		}

		json.NewEncoder(w).Encode(user)

		return
	}

	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(user)
}

func DeleteUser(w http.ResponseWriter, r *http.Request, userService service.User) {
	idStr := chi.URLParam(r, "id")

	id, err := strconv.ParseInt(idStr, 10, 64)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)

		return
	}

	if user, err := userService.DeleteUser(id); err != nil {
		w.WriteHeader(http.StatusNotFound)
		json.NewEncoder(w).Encode(user)

		return
	}

	w.WriteHeader(http.StatusNoContent)
}

func UpdateUser(w http.ResponseWriter, r *http.Request, userService service.User) {
	var user entity.User
	if err := json.NewDecoder(r.Body).Decode(&user); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)

		return
	}

	if err := userService.UpdateUser(user); err != nil {
		w.WriteHeader(http.StatusNotFound)
		json.NewEncoder(w).Encode(user)

		return
	}

	w.WriteHeader(http.StatusOK)
	json.NewEncoder(w).Encode(user)
}
