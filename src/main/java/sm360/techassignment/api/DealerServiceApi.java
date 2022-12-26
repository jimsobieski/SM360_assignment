package sm360.techassignment.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sm360.techassignment.dto.DealerDTO;
import sm360.techassignment.mapper.DealerMapper;
import sm360.techassignment.service.DealerService;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DealerServiceApi {

	private final DealerService dealerService;
	
	public DealerDTO findById(UUID id) {
		return DealerMapper.mapEntityToDTO(dealerService.findById(id));
	}
	
	public DealerDTO create(String name) {
		return DealerMapper.mapEntityToDTO(dealerService.create(name));
	}
	
	public List<DealerDTO> findAll(){
		return dealerService.findAll().stream().map(DealerMapper::mapEntityToDTO)
				.collect(Collectors.toList());
	}
}
