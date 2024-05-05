package by.bsuir.test_rw.model.dto.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponseTO {
    private String content;
    private Long issueId;
    private Long id;
}
