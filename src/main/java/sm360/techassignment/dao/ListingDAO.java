package sm360.techassignment.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import sm360.techassignment.entity.Listing;
import sm360.techassignment.enumeration.ListingState;

public interface ListingDAO extends CrudRepository<Listing, UUID>{

	@Query("SELECT lis FROM Listing lis"
			+ " INNER JOIN lis.dealer dea"
			+ " WHERE dea.id = :dealerId"
			+ " AND lis.state = :state")
	List<Listing> findListingByDealerIdAndState(@Param("dealerId") UUID dealerId, @Param("state") ListingState state);
}
