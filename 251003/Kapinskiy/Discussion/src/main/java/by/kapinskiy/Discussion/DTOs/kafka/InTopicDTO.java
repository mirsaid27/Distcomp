package by.kapinskiy.Discussion.DTOs.kafka;


import by.kapinskiy.Discussion.DTOs.Requests.NoteRequestDTO;

public class InTopicDTO {
    private String method;
    private NoteRequestDTO noteRequestDTO;
    private String status;

    public InTopicDTO(String method, NoteRequestDTO noteRequestDTO, String status) {
        this.method = method;
        this.noteRequestDTO = noteRequestDTO;
        this.status = status;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public NoteRequestDTO getNoteRequestDTO() {
        return noteRequestDTO;
    }

    public void setNoteRequestDTO(NoteRequestDTO noteRequestDTO) {
        this.noteRequestDTO = noteRequestDTO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InTopicDTO() {
    }
}
