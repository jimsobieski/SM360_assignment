package sm360.techassignment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import sm360.techassignment.dao.ListingDAO;
import sm360.techassignment.entity.Listing;
import sm360.techassignment.enumeration.ListingState;
import sm360.techassignment.exception.EntityNotFoundException;
import sm360.techassignment.exception.TierLimitExceededException;
import sm360.techassignment.properties.Sm360Properties;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Manage interactions with listing entity
 * 
 * @author staton
 *
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ListingService {

	private final ListingDAO listingDAO;
	private final Sm360Properties sm360Properties;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ListingService.class);
	
	/**
	 * find a listing by id
	 * 
	 * @param id
	 * @return a listing
	 */
	public Listing findById(UUID id) {
		return listingDAO.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}
	
	/**
	 * search in database a list of listing for a dealer and a state
	 * 
	 * @param dealerId
	 * @param state
	 * @return a list of listing for a dealer and a state
	 */
	public List<Listing> findListingByDealerIdAndState(UUID dealerId, ListingState state) {
		return listingDAO.findListingByDealerIdAndState(dealerId, state);
	}
	
	/**
	 * count the number of listing for a dealer and a state
	 * 
	 * @param dealerId
	 * @param state
	 * @return the number of listing for a dealer and a state
	 */
	public int countListingByDealerIdAndState(UUID dealerId, ListingState state) {
		return findListingByDealerIdAndState(dealerId, state).size();
	}
	
	/**
	 * create a listing with given params
	 * <p>
	 * createdAt is initialized to now
	 * <p>
	 * state is initialized to draft
	 * 
	 * @param listing
	 * @return a new listing
	 */
	public Listing create(Listing listing) {
		listing.setState(ListingState.draft);
		listing.setCreatedAt(LocalDate.now());
		LOGGER.debug("Creating new listing with vehicle {} and price {}",
				listing.getVehicle(), listing.getPrice());
		return listingDAO.save(listing);
	}
	
	/**
	 * Update price and vehicule of listing
	 * 
	 * @param listing
	 * @return a listing updated
	 */
	public Listing update(Listing listing) {
		var listingToUpdate = findById(listing.getId());
		listingToUpdate.setPrice(listing.getPrice());
		listingToUpdate.setVehicle(listing.getVehicle());
		LOGGER.debug("Updating listing with id {}. New price: {}, new vehicle: {}",
				listing.getId(), listing.getPrice(), listing.getVehicle());
		return listingDAO.save(listingToUpdate);
		
	}
	
	/**
	 * Find the listing by id, check if the dealer's tier limit is exceeded 
	 * <p>
	 * then set the state to published and save
	 * 
	 * @param listingId
	 * @return the listing with state published
	 */
	public Listing publish(UUID listingId, Boolean overwrite) {
		var listing = findById(listingId);
		var dealerId = listing.getDealer().getId();
		var countPublishedListing = countListingByDealerIdAndState(dealerId, ListingState.published);
		if(countPublishedListing >= sm360Properties.getTierlimit()) {
			if(overwrite) {
				unpublishOldestListingByDealerId(dealerId);
			} else {
				LOGGER.warn("TierLimit is exceeded");
				throw new TierLimitExceededException();
			}
			
		}
		listing.setState(ListingState.published);
		LOGGER.debug("Publishing listing with id {}", listingId);
		return listingDAO.save(listing);
	}
	
	/**
	 * Find the listing by id, set the state to draft and save
	 * @param listingId
	 * @return the listing with state draft
	 */
	public Listing unpublish(UUID listingId) {
		var listing = findById(listingId);
		listing.setState(ListingState.draft);
		LOGGER.debug("Unpublish listing with id {}", listingId);
		return listingDAO.save(listing);
	}
	
	/**
	 * Find the oldest publish listing of a dealer and set it to draft 
	 * 
	 * @param dealerId
	 */
	public void unpublishOldestListingByDealerId(UUID dealerId) {
		listingDAO.setStateOldestPublishListingByDealerId(dealerId, ListingState.draft);
	}
}
