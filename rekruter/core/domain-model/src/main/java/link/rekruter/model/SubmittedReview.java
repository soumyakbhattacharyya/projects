package link.rekruter.model;

import java.util.Date;

import lombok.Data;

@Data
public class SubmittedReview {

	private String id;
	private String feedbackText;
	private Interviewer submittedBy;
	private Date createdOn;
	private Date updatedOn;	
}
