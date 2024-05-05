package by.bsuir.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    private Long id;
    private String title;
    private String content;
    private Timestamp created;
    private Timestamp modified;
    private Long editorId;
    private Long TagId;
}
