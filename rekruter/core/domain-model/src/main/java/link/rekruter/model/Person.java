package link.rekruter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * one who receives a notification
 * 
 * @author Soumyak
 *
 */
public class Person {

	/**
	 * unique id of the recipient
	 */
	private String id;

	/**
	 * name of the recipient
	 */
	private String name;

	/**
	 * email of the recipient
	 */
	private String email;
}
