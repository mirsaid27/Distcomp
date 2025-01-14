package by.bsuir.dc.impl.author;

import by.bsuir.dc.impl.author.model.Author;
import by.bsuir.dc.impl.author.model.AuthorRequest;
import by.bsuir.dc.impl.author.model.AuthorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface AuthorMapper {
    AuthorResponse toAuthorResponseDto(Author author);
    List<AuthorResponse> toAuthorResponseDto(Iterable<Author> author);
    List<AuthorResponse> toAuthorRequestDto(Iterable<Author> author);
    AuthorRequest toAuthorRequestDto(Author author);
    Author toAuthorDto(AuthorRequest request);
    List<Author> toAuthorDto(Iterable<AuthorRequest> requests);
}
