package sm360.techassignment.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sm360.techassignment.dto.ProfileDTO;
import sm360.techassignment.enumeration.ProfileEnum;
import sm360.techassignment.mapper.ProfileMapper;
import sm360.techassignment.service.ProfileService;

@Service
@AllArgsConstructor
public class ProfileServiceApi {

	private ProfileService profileService;
	
	public ProfileDTO findByCode(ProfileEnum profileEnum) {
		return ProfileMapper.mapEntityToDTO(profileService.findById(profileEnum));
	}
	
	public List<ProfileDTO> findAll(){
		return profileService.findAll().stream().map(ProfileMapper::mapEntityToDTO)
		.collect(Collectors.toList());
	}
}
