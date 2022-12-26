package sm360.techassignment.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import sm360.techassignment.entity.Listing;
import sm360.techassignment.enumeration.ListingState;

public interface ListingDAO extends CrudRepository<Listing, UUID>{
	
	/**
	 * Return all listing
	 */
	List<Listing> findAll();

	/**
	 * search in database a list of listing for a dealer and a state
	 * 
	 * @param dealerId
	 * @param state
	 * @return a list of listing for a dealer and a state
	 */
	@Query("SELECT lis FROM Listing lis"
			+ " INNER JOIN lis.dealer dea"
			+ " WHERE dea.id = :dealerId"
			+ " AND lis.state = :state")
	List<Listing> findListingByDealerIdAndState(@Param("dealerId") UUID dealerId, @Param("state") ListingState state);
	
	/**
	 * Find the oldest publish listing of a dealer and set it to draft 
	 * 
	 * @param dealerId
	 * @param state
	 */
	@Modifying
	@Query("UPDATE Listing SET state = :state"
	+ " WHERE dealer.id = :dealerId"
	+ " AND createdAt = (SELECT MIN(createdAt)"
	+ " FROM Listing WHERE dealer.id = :dealerId AND state = 'published')")
	void setStateOldestPublishListingByDealerId(@Param("dealerId") UUID dealerId, @Param("state") ListingState state);
}
