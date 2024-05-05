package com.example.rv.impl.news.dto;

import java.math.BigInteger;
import java.util.Date;

public record NewsResponseTo(
        BigInteger id,
        BigInteger editorId,
        String title,
        String content,
        Date created,
        Date modified
){

}
