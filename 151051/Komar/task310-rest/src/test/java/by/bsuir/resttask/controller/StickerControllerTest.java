package by.bsuir.resttask.controller;

import by.bsuir.resttask.dto.request.StickerRequestTo;
import by.bsuir.resttask.dto.response.StickerResponseTo;

public class StickerControllerTest extends RestControllerTest<StickerRequestTo, StickerResponseTo> {

    @Override
    protected StickerRequestTo getRequestTo() {
        return new StickerRequestTo(null, "sticker" + RANDOM_NUMBER_GENERATOR.nextInt());
    }

    @Override
    protected StickerRequestTo getUpdateRequestTo(StickerRequestTo originalRequest, Long updateEntityId) {
        return new StickerRequestTo(updateEntityId,
                originalRequest.name() + RANDOM_NUMBER_GENERATOR.nextInt());
    }

    @Override
    protected String getRequestsMappingPath() {
        return "/stickers";
    }
}
