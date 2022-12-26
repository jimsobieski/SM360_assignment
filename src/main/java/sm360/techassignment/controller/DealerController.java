package sm360.techassignment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import sm360.techassignment.api.DealerServiceApi;
import sm360.techassignment.dto.DealerDTO;

@RestController
@RequestMapping("dealer")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DealerController {

	private final DealerServiceApi dealerServiceApi;
	
	@GetMapping("/{id}")
	public DealerDTO findDealerById(@PathVariable("id") UUID id){
		return dealerServiceApi.findById(id);
	}
	
	@PutMapping
	public DealerDTO createDealer(@RequestParam("name") String name) {
		return dealerServiceApi.create(name);
	}
	
	@GetMapping
	public List<DealerDTO> findAllDealer(){
		return dealerServiceApi.findAll();
	}
}
