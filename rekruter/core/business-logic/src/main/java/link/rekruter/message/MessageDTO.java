package link.rekruter.message;

import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MessageDTO {

    private Long id;

    @Size(max = 255)
    private String sender;

    @Size(max = 255)
    private String subject;

    @Size(max = 255)
    private String body;

    private LocalDateTime sendAt;

    private Long messages;

}
