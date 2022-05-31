package in.paymatrix.rest_ref_impl.exception;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExceptionResponse {

	/**
	 * when did this exception happen
	 */
	@NotNull
	private final Date timestamp;
	
	/**
	 * actual exception
	 */
	@NotNull
	private final String userFriendlyMessage;
	
	/**
	 * detailed stacktrace (optional)
	 */
	private String message;
	
	private Throwable rootCause;
	
	public static ExceptionResponseBuilder builder(Date timestamp, String userFriendlyMessage) {
		return new ExceptionResponseBuilder().timestamp(timestamp).userFriendlyMessage(userFriendlyMessage);
	}

}
