package link.rekruter.opening_manager.opening;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OpeningDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String shortDescription;

}
