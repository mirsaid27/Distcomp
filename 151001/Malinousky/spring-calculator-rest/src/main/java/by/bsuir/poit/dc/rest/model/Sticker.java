package by.bsuir.poit.dc.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Name Surname
 * 
 */
@Entity
@Table(name = "sticker")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Size(min = 2, max = 34)
    @Column(name = "name",
	length = 34,
	nullable = false,
	unique = true)
    private String name;
}
