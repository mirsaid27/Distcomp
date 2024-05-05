package by.bsuir.news.dto.request;


import by.bsuir.news.entity.Note;
import by.bsuir.news.entity.NoteKey;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequestTo {
    private Long id = 0L;
    @Size(min = 2, max = 2048)
    private String content;
    private Long newsId;
    public static Note fromRequest(NoteRequestTo request) {
        Note note = new Note();
        note.setContent(request.content);
        note.setKey(new NoteKey(Locale.ENGLISH, request.id, request.newsId));
        return note;
    }
}
