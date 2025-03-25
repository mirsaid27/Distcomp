package by.bsuir.distcomp.dto.mapper;

import by.bsuir.distcomp.dto.request.ReactionRequestTo;
import by.bsuir.distcomp.dto.response.ReactionResponseTo;
import by.bsuir.distcomp.entity.Reaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReactionMapper {
    ReactionResponseTo toDto(Reaction reaction);
    Reaction toEntity(ReactionRequestTo reactionDTO);
}
