package by.bsuir.poit.dc.rest.api.dto.response;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ProtobufClass
public class CommentDto {
    private long id;
    private String content;
    private long tweetId;
}