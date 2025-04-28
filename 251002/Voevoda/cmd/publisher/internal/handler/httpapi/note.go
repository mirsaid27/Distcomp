package httpapi

import (
	"bytes"
	"io"
	"log/slog"
	"net/http"
	"strings"
)

const discussionBaseURL = "http://localhost:24130/api/v1.0/notes"

func NewNoteRedirect() http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Construct the target URL while preserving the path
		targetURL := discussionBaseURL
		if r.URL.Path != "" {
			// Extract the part after "/notes" and append it to the target URL
			pathParts := strings.Split(r.URL.Path, "/notes")
			if len(pathParts) > 1 {
				targetURL += pathParts[1]
			}
		}

		// Add query string if present
		if r.URL.RawQuery != "" {
			targetURL += "?" + r.URL.RawQuery
		}

		req, err := http.NewRequest(r.Method, targetURL, r.Body)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}

		// Copy all headers from the original request
		for key, values := range r.Header {
			for _, value := range values {
				req.Header.Add(key, value)
			}
		}

		// Remove any headers that might reveal the redirect
		req.Header.Del("Referer")

		resp, err := http.DefaultClient.Do(req)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}

		defer resp.Body.Close()

		// Copy all headers from the response
		for key, values := range resp.Header {
			for _, value := range values {
				w.Header().Add(key, value)
			}
		}

		// Remove any headers that might reveal the redirect
		w.Header().Del("Location")
		w.Header().Del("Server")

		w.WriteHeader(resp.StatusCode)

		responseBody, _ := io.ReadAll(resp.Body)
		resp.Body = io.NopCloser(bytes.NewBuffer(responseBody))
		slog.Info(
			"Redirected to discussion",
			slog.String("method", r.Method),
			slog.String("url", r.URL.String()),
			slog.String("target_url", targetURL),
			slog.Int("status", resp.StatusCode),
			slog.String("response", string(responseBody)),
		)

		// Copy the response body
		if _, err := io.Copy(w, resp.Body); err != nil {
			http.Error(w, "Error processing response", http.StatusInternalServerError)
			return
		}
	}
}
