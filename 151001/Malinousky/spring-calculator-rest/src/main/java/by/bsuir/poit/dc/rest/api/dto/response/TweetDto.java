package by.bsuir.poit.dc.rest.api.dto.response;

import by.bsuir.poit.dc.rest.model.Tweet;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO for {@link Tweet}
 */
@Data
@Builder
@ProtobufClass
@AllArgsConstructor
@NoArgsConstructor
public class TweetDto {
    private long id;
    private String title;
    private String content;
    private Date created;
    private Date modified;
    private long userId;
}