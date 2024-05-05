package com.example.lab2.Mapper;

import com.example.lab2.DTO.CreatorRequestTo;
import com.example.lab2.DTO.CreatorResponseTo;
import com.example.lab2.Model.Creator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
    Creator creatorRequestToCreator(CreatorRequestTo creatorRequestTo);

    CreatorResponseTo creatorToCreatorResponse(Creator creator);
}
