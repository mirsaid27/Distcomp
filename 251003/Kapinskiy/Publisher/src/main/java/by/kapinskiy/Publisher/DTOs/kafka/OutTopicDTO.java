package by.kapinskiy.Publisher.DTOs.kafka;

import by.kapinskiy.Publisher.DTOs.Requests.NoteRequestDTO;
import by.kapinskiy.Publisher.DTOs.Responses.NoteResponseDTO;
import org.springframework.http.HttpMethod;

import java.util.List;

public class OutTopicDTO {
    private NoteResponseDTO noteResponseDTO;
    private List<NoteResponseDTO> noteResponsesListDTO;
    private String status;
    private String error;

    public NoteResponseDTO getNoteResponseDTO() {
        return noteResponseDTO;
    }

    public void setNoteResponseDTO(NoteResponseDTO noteResponseDTO) {
        this.noteResponseDTO = noteResponseDTO;
    }

    public List<NoteResponseDTO> getNoteResponsesListDTO() {
        return noteResponsesListDTO;
    }

    public void setNoteResponsesListDTO(List<NoteResponseDTO> noteResponsesListDTO) {
        this.noteResponsesListDTO = noteResponsesListDTO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public OutTopicDTO() {
    }
}
