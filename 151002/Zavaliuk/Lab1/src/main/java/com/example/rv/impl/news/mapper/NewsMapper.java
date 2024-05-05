package com.example.rv.impl.news.mapper;

import com.example.rv.impl.editor.Editor;
import com.example.rv.impl.news.News;
import com.example.rv.impl.news.dto.NewsRequestTo;
import com.example.rv.impl.news.dto.NewsResponseTo;

import java.util.List;

public interface NewsMapper {

    NewsRequestTo newsToRequestTo(News news);

    List<NewsRequestTo> newsToRequestTo(Iterable<News> news);

    News dtoToEntity(NewsRequestTo newsRequestTo, Editor editor);

    NewsResponseTo newsToResponseTo(News news);

    List<NewsResponseTo> newsToResponseTo(Iterable<News> news);
}
