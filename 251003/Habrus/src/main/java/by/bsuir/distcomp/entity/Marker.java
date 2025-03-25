package by.bsuir.distcomp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marker implements Identifiable {

    private Long id;
    private String Name;

}
