package link.rekruter.person;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PersonDTO {

    private Long id;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String userId;

    @NotNull
    private Boolean interviewer;

    private Long recipient;

    private Long invitees;

}
