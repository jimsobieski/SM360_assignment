package sm360.techassignment.mapper;

import org.springframework.util.ObjectUtils;

import sm360.techassignment.dto.ListingDTO;
import sm360.techassignment.entity.Dealer;
import sm360.techassignment.entity.Listing;
import sm360.techassignment.enumeration.ListingState;

/**
 * Mapper for Listing entity
 * 
 * @author staton
 *
 */
public class ListingMapper {

	/**
	 * Transform a Listing into a ListingDTO
	 * 
	 * @param entity
	 * @return a ListingDTO
	 */
	public static ListingDTO mapEntityToDTO(Listing entity) {
		var builder = ListingDTO.builder();
		builder.createdAt(entity.getCreatedAt())
			.id(entity.getId())
			.price(entity.getPrice())
			.isPublished(entity.getState() == ListingState.published)
			.vehicle(entity.getVehicle());
		
		if(!ObjectUtils.isEmpty(entity.getDealer())) {
			builder.dealerId(entity.getDealer().getId());
		}
		
		return builder.build();
	}
	
	/**
	 * Transform a ListingDTO into a Listing
	 * 
	 * @param entity
	 * @return a Listing
	 */
	public static Listing mapDTOToEntity(ListingDTO dto) {
		  var builder = Listing.builder();
		  builder.createdAt(dto.getCreatedAt())
		    .id(dto.getId())
		    .price(dto.getPrice())
		    .vehicle(dto.getVehicle());

		  if(!ObjectUtils.isEmpty(dto.getIsPublished())) {
			  builder.state(dto.getIsPublished() ? ListingState.published : ListingState.draft);
		  }
		  
		  if(!ObjectUtils.isEmpty(dto.getDealerId())) {
		    var dealer = Dealer.builder().id(dto.getDealerId()).build();
		    builder.dealer(dealer);
		  }

		  return builder.build();
		}
}
