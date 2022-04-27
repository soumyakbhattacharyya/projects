package link.rekruter.resume;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResumeDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String fileName;

    @NotNull
    private String content;

    @Size(max = 255)
    private String downloadLocation;

}
