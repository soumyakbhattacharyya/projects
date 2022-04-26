package link.rekruter.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Interview {

	private String id;
	private String title;
	private String description;
	private Date appointment;
	private Long durationOfInterview;
	private String location;
	private Map<String, String> interviewInfo;
	private Candidate candidate;
	private String createdBy; // user who created the interview
	private Date createdOn;
	private boolean isPrivate;
	private boolean cancelled;
	private Interviewer[] invitees;
	private List<String> messages;	

}
