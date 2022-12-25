/**
 * 
 */
package sm360.techassignment.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Access to data properties with sm360 prefix
 * <p>
 * I choose this way to implements the tierlimit instead of store it in database
 * 
 * @author staton
 *
 */
@Configuration
@ConfigurationProperties(prefix = "sm360")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sm360Properties {

	private Integer tierlimit;
}
