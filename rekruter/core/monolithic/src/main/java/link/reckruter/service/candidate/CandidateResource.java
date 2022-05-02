package link.reckruter.service.candidate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResource {
	
	/**
	 * unique id of the candidate
	 */
	private String id;

	/**
	 * first name of the candidate
	 */
	private String firstName;

	/**
	 * last name of the candidate
	 */
	private String lastName;

	/**
	 * phone number of the candidate
	 */
	private String phone;

	/**
	 * brief description about the candidate
	 */
	private String description;


}
