package link.rekruter.service;

import link.rekruter.model.Evaluation;

public interface InterviewService {

	/**
	 * schedules an interview
	 */
	void scheduleInterview();

	/**
	 * captures evaluation from an interview
	 */
	void capture(Evaluation evaluation);

}
