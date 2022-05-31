package in.paymatrix.rest_ref_impl.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Skill {

    private Long id;

    @Size(max = 255)
    private String domain;

    @Size(max = 255)
    private String programmingLanguage;

    @Size(max = 255)
    private String functional;

    private Long employee;

}
