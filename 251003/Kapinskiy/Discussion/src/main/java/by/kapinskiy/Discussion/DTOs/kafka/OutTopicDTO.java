package by.kapinskiy.Discussion.DTOs.kafka;


import by.kapinskiy.Discussion.DTOs.Responses.NoteResponseDTO;

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

    public OutTopicDTO(NoteResponseDTO noteResponseDTO, String status) {
        this.noteResponseDTO = noteResponseDTO;
        this.status = status;
    }

    public OutTopicDTO( String error, String status) {
        this.status = status;
        this.error = error;
    }

    public OutTopicDTO(List<NoteResponseDTO> noteResponsesListDTO, String status) {
        this.status = status;
        this.noteResponsesListDTO = noteResponsesListDTO;
    }

    public List<NoteResponseDTO> getNoteResponsesListDTO() {
        return noteResponsesListDTO;
    }

    public void setNoteResponsesListDTO(List<NoteResponseDTO> noteResponsesListDTO) {
        this.noteResponsesListDTO = noteResponsesListDTO;
    }

    public OutTopicDTO() {
    }
}
