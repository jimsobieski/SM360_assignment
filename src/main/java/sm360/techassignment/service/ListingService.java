package sm360.techassignment.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sm360.techassignment.dao.ListingDAO;
import sm360.techassignment.entity.Listing;
import sm360.techassignment.enumeration.ListingState;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Manage interactions with listing entity
 * 
 * @author staton
 *
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ListingService {

	private final ListingDAO listingDAO;
	
	/**
	 * find a listing by id
	 * 
	 * @param id
	 * @return a listing
	 */
	public Listing findById(UUID id) {
		return listingDAO.findById(id).orElseThrow(() -> new RuntimeException());
	}
	
	
	public List<Listing> findListingByDealerIdAndState(UUID dealerId, ListingState state) {
		return listingDAO.findListingByDealerIdAndState(dealerId, state);
	}
	
	/**
	 * create a listing with given params
	 * createdAt is initialized to now
	 * state is initialized to draft
	 * 
	 * @param listing
	 * @return a new listing
	 */
	public Listing createListing(Listing listing) {
		listing.setState(ListingState.draft);
		listing.setCreatedAt(LocalDate.now());
		return listingDAO.save(listing);
	}
	
}
