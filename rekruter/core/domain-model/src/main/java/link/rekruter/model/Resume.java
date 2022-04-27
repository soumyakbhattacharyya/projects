package link.rekruter.model;

import lombok.Data;

@Data
public class Resume {

	/**
	 * unique identifier of the resume
	 */
	private String id;
	/**
	 * filename of the resume
	 */
	private String fileName;

	/**
	 * content of the file in Base64 encoded format
	 */
	private String content;

	/**
	 * hyperlinked location from where the resume can be downloaded
	 */
	private String downloadLocation;

}
