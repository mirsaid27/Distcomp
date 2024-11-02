package com.padabied.dc_rest.mapper;

import com.padabied.dc_rest.model.Sticker;
import com.padabied.dc_rest.model.dto.requests.StickerRequestTo;
import com.padabied.dc_rest.model.dto.responses.StickerResponseTo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-30T21:11:04+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class StickerMapperImpl implements StickerMapper {

    @Override
    public StickerResponseTo toResponse(Sticker sticker) {
        if ( sticker == null ) {
            return null;
        }

        StickerResponseTo stickerResponseTo = new StickerResponseTo();

        stickerResponseTo.setId( sticker.getId() );
        stickerResponseTo.setName( sticker.getName() );

        return stickerResponseTo;
    }

    @Override
    public Sticker toEntity(StickerRequestTo stickerRequestDto) {
        if ( stickerRequestDto == null ) {
            return null;
        }

        Sticker sticker = new Sticker();

        sticker.setName( stickerRequestDto.getName() );

        return sticker;
    }
}
