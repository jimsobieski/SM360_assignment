package sm360.techassignment.mapper;

import org.springframework.util.ObjectUtils;

import sm360.techassignment.dto.DealerDTO;
import sm360.techassignment.entity.Dealer;
import sm360.techassignment.exception.EmptyObjectException;

public class DealerMapper {

	/**
	 * Transform a Dealer into a DealerDTO
	 * 
	 * @param entity
	 * @return a DealerDTO
	 */
	public static DealerDTO mapEntityToDTO(Dealer entity) {
		if(ObjectUtils.isEmpty(entity)) {
			throw new EmptyObjectException();
		}
		return DealerDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.build();
	}
	
	/**
	 * Transform a Dealer into a DealerDTO
	 * 
	 * @param dto
	 * @return a DealerDTO
	 */
	public static Dealer mapDTOToEntity(DealerDTO dto) {
		if(ObjectUtils.isEmpty(dto)) {
			throw new EmptyObjectException();
		}
		return Dealer.builder()
				.id(dto.getId())
				.name(dto.getName())
				.build();
	}
}
