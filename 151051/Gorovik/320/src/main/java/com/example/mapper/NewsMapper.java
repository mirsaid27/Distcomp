package com.example.mapper;

import com.example.model.News;
import com.example.request.NewsRequestTo;
import com.example.response.NewsResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsResponseTo getResponse(News news);
    List<NewsResponseTo> getListResponse(Iterable<News> newss);
    News getNews(NewsRequestTo newsRequestTo);
}