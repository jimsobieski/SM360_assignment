package sm360.techassignment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@PutMapping("/create")
	public ListingDTO save(@RequestBody ListingDTO listingDto) {
		return listingServiceApi.createListing(listingDto);
	}
	
	@GetMapping("/dealer")
	public List<ListingDTO> findListingByDealerIdAndState(@RequestParam("dealerId") UUID dealerId, ListingState state) {
		return listingServiceApi.findListingByDealerIdAndState(dealerId, state);
	}
	
	@PostMapping("{listingId}/publish")
	public ListingDTO publish(@PathVariable("listingId") UUID listingId, 
			@RequestParam(name="overwrite", 
			required = false, 
			defaultValue= "false") Boolean overwrite) {
		return listingServiceApi.publish(listingId, overwrite);
	}
}
