package in.paymatrix.rest_ref_impl.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("in.paymatrix.rest_ref_impl.entity")
@EnableJpaRepositories("in.paymatrix.rest_ref_impl.repos")
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
@EnableTransactionManagement
public class DomainConfig {
}
