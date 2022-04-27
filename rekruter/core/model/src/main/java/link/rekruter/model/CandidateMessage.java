package link.rekruter.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * messages that are exchanged between candidate and hiring organization
 * 
 * @author Soumyak
 *
 */
public class CandidateMessage {
	private String messageId;
	private List<Recipient> recipients;
	private List<Message> messages;
	private Candidate candidate;
	private boolean isPrivate;
}
