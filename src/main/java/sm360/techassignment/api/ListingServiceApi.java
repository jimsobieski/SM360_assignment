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
	
	public ListingDTO findById(UUID id){
		return ListingMapper.mapEntityToDTO(listingService.findById(id));
	}
	
	public List<ListingDTO> findAll(){
		return listingService.findAll().stream().map(ListingMapper::mapEntityToDTO)
				.collect(Collectors.toList());
	}
	
	public ListingDTO create(ListingDTO dto) {
		var entity = ListingMapper.mapDTOToEntity(dto);
		entity = listingService.create(entity);
		return ListingMapper.mapEntityToDTO(entity);
	}
	
	public ListingDTO update(ListingDTO dto) {
		var entity = ListingMapper.mapDTOToEntity(dto);
		entity = listingService.update(entity);
		return ListingMapper.mapEntityToDTO(entity);
	}
	
	public void delete(UUID id) {
		listingService.delete(id);
	}
	
	public List<ListingDTO> findListingByDealerIdAndState(UUID id, ListingState state) {
		return listingService.findListingByDealerIdAndState(id, state).stream()
				.map(ListingMapper::mapEntityToDTO).collect(Collectors.toList());
	}
	
	public ListingDTO publish(UUID listingId , Boolean overwrite) {
		return ListingMapper.mapEntityToDTO(listingService.publish(listingId, overwrite));
	}
	
	public ListingDTO unpublish(UUID listingId) {
		return ListingMapper.mapEntityToDTO(listingService.unpublish(listingId));
	}
	
}
