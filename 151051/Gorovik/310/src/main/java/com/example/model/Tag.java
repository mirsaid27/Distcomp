package com.example.model;

import com.example.model.AEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder

public class Tag extends  AEntity{
    private String name;
}