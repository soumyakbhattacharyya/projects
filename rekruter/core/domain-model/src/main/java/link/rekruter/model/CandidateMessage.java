package link.rekruter.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * messages that are exchanged between candidate and hiring party
 * 
 * @author Soumyak
 *
 */
public class CandidateMessage {
	/**
	 * unique id of the conversation thread
	 */
	private String id;
	
	/**
	 * list of recipients involved in the conversation
	 */
	private List<Person> recipients;
	
	/**
	 * collection of messages that got exchanged
	 */
	private List<Message> messages;
	
	/**
	 * the candidate with whom the messages are being exchanged 
	 */
	private Candidate candidate;
	
	/**
	 * if the conversation are to happen in private
	 */
	private boolean isPrivate;
}
