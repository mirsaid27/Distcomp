package issue

import (
	"encoding/json"
	"net/http"

	"github.com/gorilla/mux"
)

func InitRoutes(r *mux.Router) {
	r.HandleFunc("/issues", getIssuesList).Methods(http.MethodGet)
}

func getIssuesList(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusOK)

	resp := map[string]interface{}{
		"issues": []map[string]interface{}{
			{"id": 1, "title": "Fix bug", "status": "open"},
			{"id": 2, "title": "Add feature", "status": "in progress"},
		},
	}

	json.NewEncoder(w).Encode(resp)
}
