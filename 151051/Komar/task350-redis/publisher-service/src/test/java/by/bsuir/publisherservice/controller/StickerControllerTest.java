package by.bsuir.publisherservice.controller;

import by.bsuir.publisherservice.dto.request.StickerRequestTo;
import by.bsuir.publisherservice.dto.response.StickerResponseTo;

public class StickerControllerTest extends RestControllerTest<StickerRequestTo, StickerResponseTo> {

    @Override
    protected StickerRequestTo getRequestTo() {
        return new StickerRequestTo(null, "sticker" + RANDOM_NUMBER_GENERATOR.nextInt(Integer.MAX_VALUE));
    }

    @Override
    protected StickerRequestTo getUpdateRequestTo(StickerRequestTo originalRequest, Long updateEntityId) {
        return new StickerRequestTo(updateEntityId,
                                originalRequest.name() + RANDOM_NUMBER_GENERATOR.nextInt(Integer.MAX_VALUE));
    }

    @Override
    protected String getRequestsMappingPath() {
        return "/stickers";
    }
}
