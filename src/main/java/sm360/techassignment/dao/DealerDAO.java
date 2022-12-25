package sm360.techassignment.dao;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import sm360.techassignment.entity.Dealer;

/**
 * 
 * 
 * @author staton
 *
 */
public interface DealerDAO extends PagingAndSortingRepository<Dealer, UUID>{

	
}
