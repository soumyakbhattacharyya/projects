package in.paymatrix.rest_ref_impl.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.validation.OpenApiValidationAdviceTrait;

@ControllerAdvice
class ExceptionHandling implements ProblemHandling {
	
	@Override
    public boolean isCausalChainsEnabled() {
        return true;
    }
}