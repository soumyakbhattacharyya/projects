package link.rekruter.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Review {

	private String id;
	private Candidate caodidate;
	private List<Interviewer> reviewers;
	private Date createdOn;
	private List<SubmittedReview> submittedReviews;
}
