package link.rekruter.service;

import java.util.List;

import link.rekruter.model.Candidate;
import link.rekruter.model.CandidateMessage;
import link.rekruter.model.Evaluation;
import link.rekruter.model.InternalNote;
import link.rekruter.model.Message;

public interface CandidateService {

	/**
	 * registers a new candidate
	 */
	void registerCandidate();

	/**
	 * process the resume of the candidate
	 */
	void processResume();

	/**
	 * gets all candidates by criteria
	 * 
	 * @return list of all candidates
	 */
	List<Candidate> getAll();

	/**
	 * finds a specific candidate
	 * 
	 * @return a specific candidate
	 */
	Candidate findOne();

	/**
	 * notifies a candidate 
	 * 
	 * @param candidateMessage
	 */
	void notifyCandidate(CandidateMessage candidateMessage);

	/**
	 * gets all messages that have been sent to the candidate
	 * 
	 * @param candidate for whom the message is sent
	 * @return all messages that have been exchanged with the candidate
	 */
	List<Message> getAllMessages(Candidate candidate);

	/**
	 * reads a specific message
	 * 
	 * @param candidate
	 * @return a specific message
	 */
	Message readCandidateMessage(Candidate candidate, String messageId);

	/**
	 * captures internal notes about a candidate profile
	 * 
	 * @param candidate    in question
	 * @param internalNote exchanged about a candidate profile
	 */
	void captureInternalNote(Candidate candidate, InternalNote internalNote);

	/**
	 * gets all internal notes about the candidate
	 * 
	 * @param candidate
	 * @return list of internal notes around the profile
	 */
	List<InternalNote> getInternalNote(Candidate candidate);

	/**
	 * gets all feedback about the candidate
	 * 
	 * @return evaluation 
	 */
	Evaluation getAllEvaluations();
}
