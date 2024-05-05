package com.example.rv.impl.sticker.mapper;
import com.example.rv.impl.sticker.Sticker;
import com.example.rv.impl.sticker.dto.StickerRequestTo;
import com.example.rv.impl.sticker.dto.StickerResponseTo;
import com.example.rv.impl.news.News;

import java.util.List;

public interface StickerMapper {
    StickerRequestTo stickerToRequestTo(Sticker sticker);

    List<StickerRequestTo> stickerToRequestTo(Iterable<Sticker> tags);

    Sticker dtoToEntity(StickerRequestTo stickerRequestTo, List<News> news);

    StickerResponseTo stickerToResponseTo(Sticker sticker);

    List<StickerResponseTo> stickerToResponseTo(Iterable<Sticker> tags);
}
