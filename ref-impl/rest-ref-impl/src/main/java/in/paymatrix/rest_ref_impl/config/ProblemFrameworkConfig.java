package in.paymatrix.rest_ref_impl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ProblemFrameworkConfig {

	@Bean
	public ObjectMapper objectMapper() {

		/*
		 * in debug mode, stacktrace printing is enabled
		 */

		if (log.isDebugEnabled()) {
			return new ObjectMapper().registerModules(new ProblemModule().withStackTraces());
		} else {
			return new ObjectMapper().registerModules(new ProblemModule());
		}
	}
}