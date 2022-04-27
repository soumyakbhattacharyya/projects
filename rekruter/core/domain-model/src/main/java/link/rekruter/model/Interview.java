package link.rekruter.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * represents an interview
 * 
 * @author Soumyak
 *
 */
public class Interview {

	/**
	 * unique id
	 */
	private String id;

	/**
	 * title of the interview
	 */
	private String title;

	/**
	 * description
	 */
	private String description;

	/**
	 * date and time of the interview
	 */
	private Date appointment;

	/**
	 * how long the interview is supposed to last
	 */
	private Long durationOfInterview;

	/**
	 * place of interview
	 */
	private String location;

	/**
	 * a bag of kv associated with the interview
	 */
	private Map<String, String> interviewInfo;

	/**
	 * the candidate for whom this interview is
	 */
	private Candidate candidate;

	/**
	 * user who created this interview
	 */
	private String createdBy; // user who created the interview
	private Date createdOn;
	private boolean isPrivate;
	private boolean cancelled;

	/**
	 * the panel of the interview
	 */
	private Interviewer[] invitees;

}
