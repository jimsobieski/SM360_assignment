package sm360.techassignment.dao;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import sm360.techassignment.entity.Dealer;

/**
 * 
 * 
 * @author staton
 *
 */
public interface DealerDAO extends CrudRepository<Dealer, UUID>{

	
}
