package link.rekruter.model;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * represents a potential hire
 */
public class Candidate extends Person {

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

	/**
	 * resume of the candidate
	 */
	private Resume resume;

	/**
	 * time when the candidate was introduced to the system
	 */
	private Date createdOn;

	/**
	 * time when candidate detail was last updated
	 */
	private Date updatedOn;

	/**
	 * additional set of profile information about the candidate in form of key
	 * value pair
	 */
	private Map<String, String> profileData;

	/**
	 * source from where the candidate information was obtained from
	 */
	private String source;

	/**
	 * what created the candidate
	 */
	private String sourceType;

	/**
	 * represents a spoc to whom the candidate is associated with
	 */
	private Interviewer assignedTo;

	/**
	 * unique id of the opening to which the candidate is being tagged
	 */
	private String openingId;

	/**
	 * stage to which the candidate is associated with
	 */
	private Stage stage;

	/**
	 * Describes the state in which the candidate is. Can be one of ['in_process'
	 * 'hired' 'rejected' 'declined_offer' 'onhold' 'withdrawn' 'spam' 'archived'
	 * 'onhold_prospecting' 'did_not_convert']
	 */
	private STATE state;

	/**
	 * additional metadata associated with a state
	 */
	private Map<String, String> stateMetadata;
}
