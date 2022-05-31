package in.paymatrix.rest_ref_impl.model;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {

	private Long id;

	@Size(max = 20, message = "Name should be within 20 character")
	private String firstName;

	@Size(max = 255)
	private String lastName;

	@Size(max = 255)
	private String designation;

	@Positive
	private Integer age;

}
