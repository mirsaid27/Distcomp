package by.bsuir.news.dto.response;

import by.bsuir.news.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseTo {
    private Long id;
    private String content;
    private Long newsId;

    public static NoteResponseTo toResponse(Note source) {
        NoteResponseTo model = new NoteResponseTo();
        model.id = source.getKey().getId();
        model.content = source.getContent();
        model.newsId = source.getKey().getNewsId();
        return model;
    }
}
