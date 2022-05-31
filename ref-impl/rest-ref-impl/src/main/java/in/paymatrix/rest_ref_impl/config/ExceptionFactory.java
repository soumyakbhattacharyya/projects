package in.paymatrix.rest_ref_impl.config;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionFactory {

	private String code;
	private String title;
	private String message;
	private Status status;
	private String domain;
	private String subDomain;
	private Map<String, Object> parameters;
	
	public ExceptionFactory(String code, String title, Status status, String message) {
		this.code = code;
		this.title = title;
		this.message = message;
		this.domain = domain;
		this.subDomain = subDomain;
	}

	public static ThrowableProblem createOne(String code, Map<String, Object> parameters) {
		ExceptionFactory standard = GenericProblemRepository.getGenericProblemById(code);
		String titleStr = standard.getTitle();
		String[] split = titleStr.split(" ");
		String suffix = String.join("-", split);
		ProblemBuilder builder = Problem.builder()
				                        .withType(URI.create("https://OneMuthoot/CustomApp"+"/"+suffix))
				                        .withTitle(standard.getTitle())
				                        .withStatus(standard.getStatus())
				                        .withDetail(standard.getMessage());
		if (!parameters.isEmpty()) {
			parameters.entrySet().stream().forEach(new Consumer<Entry<String, Object>>() {

				@Override
				public void accept(Entry<String, Object> element) {
					builder.with(element.getKey(), element.getValue());

				}
			});
		}
		builder.with("timestamp", new Date());
		
		return builder.build();
	}

	/**
	 * 
	 * problem repository
	 * 
	 * @author soumyak
	 *
	 */
	public static class GenericProblemRepository {

		public static ExceptionFactory getGenericProblemById(String id) {
			Map<String, ExceptionFactory> problemRepository;
			problemRepository = new HashMap<>();
			ExceptionFactory g1 = new ExceptionFactory("1000", "Employee Not Found", Status.BAD_REQUEST,
					"Employee with the specific id not found");
			ExceptionFactory g2 = new ExceptionFactory("1010", "Duplicate Employee Exists",
					Status.CONFLICT, "A duplicate entry with the provided employee id exists");
			ExceptionFactory g3 = new ExceptionFactory("1020", "Skill Does Not Exist",
					Status.NOT_ACCEPTABLE, "Skill is unregistered with the system");
			problemRepository.put("1000", g1);
			problemRepository.put("1010", g2);
			problemRepository.put("1020", g3);
			return problemRepository.get(id);
		}

	}

}
