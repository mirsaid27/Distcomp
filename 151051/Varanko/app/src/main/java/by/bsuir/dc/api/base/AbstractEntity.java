package by.bsuir.dc.api.base;

import by.bsuir.dc.api.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractEntity implements Entity {
    private long id;
}
