package link.rekruter.candidate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CandidateDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    @Size(max = 255)
    private String phone;

    @Size(max = 500)
    private String description;

    @NotNull
    private Long resume;

    private Long assignedTo;

    private Long stage;

}
