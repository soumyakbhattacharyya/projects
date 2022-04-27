package link.rekruter.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

	/**
	 * unique id of the message
	 */
	private String id;

	

	/**
	 * the sender of the message
	 */
	private String sender;

	/**
	 * subject of the message
	 */
	private String subject;

	/**
	 * content of the message
	 */
	private String body;

	/**
	 * when the message was sent
	 */
	private Date sentAt;

	/**
	 * metadata about various attachments which might be there with the email
	 */
	private Map<String, String> attachments;
}
