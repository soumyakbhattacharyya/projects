package link.rekruter.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * notes that are exchanged among the internal evaluators
 * 
 * @author Soumyak
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalNote {

	/**
	 * unique id of the internal note
	 */
	private String id;
	
	/**
	 * recipients of the internal note
	 */
	private List<Person> recipients;
	
	/**
	 * messages exchanged
	 */
	private List<Message> messages;

}
