package link.rekruter.stage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StageDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

}
