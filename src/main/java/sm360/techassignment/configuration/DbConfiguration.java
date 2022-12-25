package sm360.techassignment.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//Gestion des transaction au niveau des composants avec l'annotation @Transactionnal (ou @TransctionnalWithRollback)
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DbConfiguration {

	
}
