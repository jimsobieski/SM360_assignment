package sm360.techassignment.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 
 * @author staton
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "sm360.techassignment.dao" })
@PropertySource("classpath:application.properties")
public class DbConfiguration {

	
}
