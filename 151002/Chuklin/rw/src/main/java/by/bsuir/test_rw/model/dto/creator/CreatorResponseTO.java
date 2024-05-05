package by.bsuir.test_rw.model.dto.creator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatorResponseTO {
    private String login;
    private String firstname;
    private String lastname;
    private Long id;
}
