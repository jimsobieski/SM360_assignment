package sm360.techassignment.mapper;

import org.apache.commons.lang3.ObjectUtils;

import sm360.techassignment.dto.ProfileDTO;
import sm360.techassignment.entity.Profile;
import sm360.techassignment.enumeration.ProfileEnum;
import sm360.techassignment.exception.EmptyObjectException;

public class ProfileMapper {

	public static ProfileDTO mapEntityToDTO(Profile entity) {
		if(ObjectUtils.isEmpty(entity)) {
			throw new EmptyObjectException();
		}
		return ProfileDTO.builder()
				.code(entity.getCode().name())
				.maxPublications(entity.getMaxPublications())
				.build();
	}
	
	public static Profile mapDTOToEntity(ProfileDTO dto) {
		if(ObjectUtils.isEmpty(dto)) {
			throw new EmptyObjectException();
		}
		return Profile.builder()
				.code(ProfileEnum.valueOf(dto.getCode()))
				.maxPublications(dto.getMaxPublications())
				.build();
	}
}
