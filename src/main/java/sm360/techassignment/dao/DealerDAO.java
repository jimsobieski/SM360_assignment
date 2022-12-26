package sm360.techassignment.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import sm360.techassignment.entity.Dealer;

/**
 * 
 * 
 * @author staton
 *
 */
public interface DealerDAO extends CrudRepository<Dealer, UUID>{

	List<Dealer> findAll();
	
	@Query("SELECT COUNT(d) > 0"
			+ " FROM Dealer d"
			+ " WHERE d.name = :name")
	boolean dealerNameAlreadyExist(@Param("name") String name);
}
