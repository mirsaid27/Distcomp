package by.bsuir.distcomp.dto.mapper;

import by.bsuir.distcomp.dto.request.AuthorRequestTo;
import by.bsuir.distcomp.dto.response.AuthorResponseTo;
import by.bsuir.distcomp.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorResponseTo toDto(Author author);
    Author toEntity(AuthorRequestTo authorDTO);
}
