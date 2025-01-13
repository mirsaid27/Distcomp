package by.bsuir.publisherservice.dto.response;

import java.io.Serializable;

public record StickerResponseTo(
    Long id,
    String name
) implements Serializable {
    
}
