package by.bsuir.poit.dc.rest.api.dto.response;

import by.bsuir.poit.dc.rest.model.Sticker;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Sticker}
 */
@Data
@Builder
@ProtobufClass
@AllArgsConstructor
@NoArgsConstructor
public class StickerDto {
    private Long id;
    private String name;
}