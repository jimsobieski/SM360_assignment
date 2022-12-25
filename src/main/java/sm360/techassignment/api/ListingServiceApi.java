package sm360.techassignment.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sm360.techassignment.dto.ListingDTO;
import sm360.techassignment.enumeration.ListingState;
import sm360.techassignment.mapper.ListingMapper;
import sm360.techassignment.service.ListingService;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ListingServiceApi {

	private final ListingService listingService;
	
	public ListingDTO createListing(ListingDTO dto) {
		var entity = ListingMapper.mapDTOToEntity(dto);
		entity = listingService.createListing(entity);
		return ListingMapper.mapEntityToDTO(entity);
	}
	
	public List<ListingDTO> findListingByDealerIdAndState(UUID id, ListingState state) {
		return listingService.findListingByDealerIdAndState(id, state).stream()
				.map(ListingMapper::mapEntityToDTO).collect(Collectors.toList());
	}
}
