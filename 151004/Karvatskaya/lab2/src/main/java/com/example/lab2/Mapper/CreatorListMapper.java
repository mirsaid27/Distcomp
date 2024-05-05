package com.example.lab2.Mapper;

import com.example.lab2.DTO.CreatorRequestTo;
import com.example.lab2.DTO.CreatorResponseTo;
import com.example.lab2.Model.Creator;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CreatorMapper.class)
public interface CreatorListMapper {
    List<Creator> toCreatorList(List<CreatorRequestTo> creators);

    List<CreatorResponseTo> toCreatorResponseList(List<Creator> creators);
}
