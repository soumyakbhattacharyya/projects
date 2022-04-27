package link.rekruter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * one who interviews a candidate
 * 
 * @author Soumyak
 *
 */
public class Interviewer extends Recipient {

	/**
	 * user id of the interviewer
	 */
	private String userId;

}
