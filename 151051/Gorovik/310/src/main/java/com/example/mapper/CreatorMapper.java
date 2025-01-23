package com.example.mapper;

import com.example.model.Creator;
import com.example.request.CreatorRequestTo;
import com.example.response.CreatorResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface CreatorMapper {
    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    CreatorResponseTo getResponse(Creator creator);

    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    List<CreatorResponseTo> getListResponse(Iterable<Creator> creators);

    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    Creator getCreator(CreatorRequestTo creatorRequestTo);
}