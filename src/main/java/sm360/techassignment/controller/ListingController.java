package sm360.techassignment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import sm360.techassignment.api.ListingServiceApi;
import sm360.techassignment.dto.ListingDTO;
import sm360.techassignment.enumeration.ListingState;

@RestController
@RequestMapping("listing")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ListingController {

	private ListingServiceApi listingServiceApi;
	
	@GetMapping("/{id}")
	public ListingDTO findListingById(@PathVariable("id") UUID id){
		return listingServiceApi.findById(id);
	}
	
	@PutMapping
	public ListingDTO createListing(@RequestBody ListingDTO listingDto) {
		return listingServiceApi.create(listingDto);
	}
	
	@PostMapping
	public ListingDTO updateListing(@RequestBody ListingDTO listingDto) {
		return listingServiceApi.update(listingDto);
	}
	
	@DeleteMapping("/{id}")
	public void deleteListing(@PathVariable("id") UUID id) {
		listingServiceApi.delete(id);
	}
	
	@GetMapping
	public List<ListingDTO> findAllListing(){
		return listingServiceApi.findAll();
	}
	
	@GetMapping("/dealer")
	public List<ListingDTO> findListingByDealerIdAndState(@RequestParam("dealerId") UUID dealerId, ListingState state) {
		return listingServiceApi.findListingByDealerIdAndState(dealerId, state);
	}
	
	@PostMapping("{listingId}/publish")
	public ListingDTO publishListing(@PathVariable("listingId") UUID listingId, 
			@RequestParam(name="overwrite", 
			required = false, 
			defaultValue= "false") Boolean overwrite) {
		return listingServiceApi.publish(listingId, overwrite);
	}
	
	@PostMapping("{listingId}/unpublish")
	public ListingDTO unpublishListing(@PathVariable("listingId") UUID listingId) {
		return listingServiceApi.unpublish(listingId);
	}
}
