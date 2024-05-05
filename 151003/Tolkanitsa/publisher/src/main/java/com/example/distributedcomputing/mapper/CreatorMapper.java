package com.example.distributedcomputing.mapper;

import com.example.distributedcomputing.model.entity.Creator;
import com.example.distributedcomputing.model.request.CreatorRequestTo;
import com.example.distributedcomputing.model.response.CreatorResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
    Creator dtoToEntity(CreatorRequestTo creatorRequestTo);
    List<Creator> dtoToEntity(Iterable<Creator> editors);

    CreatorResponseTo entityToDto(Creator creator);

    List<CreatorResponseTo> entityToDto(Iterable<Creator> editors);
}
