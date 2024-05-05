package by.bsuir.poit.dc.rest.api.dto.response;

import by.bsuir.poit.dc.rest.model.Editor;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Editor}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ProtobufClass
public class EditorDto {
    private Long id;
    private String login;
    private String firstname;
    private String lastname;
}