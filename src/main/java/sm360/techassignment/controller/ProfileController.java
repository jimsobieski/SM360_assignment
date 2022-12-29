package sm360.techassignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import sm360.techassignment.api.ProfileServiceApi;
import sm360.techassignment.dto.ProfileDTO;
import sm360.techassignment.enumeration.ProfileEnum;

@RestController("SmProfileController")
@RequestMapping("profile")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProfileController {
	
	private ProfileServiceApi profileServiceApi;
	
	@GetMapping("/{code}")
	public ProfileDTO findProfileByCode(@PathVariable("code") ProfileEnum code){
		return profileServiceApi.findByCode(code);
	}
	
	@GetMapping
	public List<ProfileDTO> findAllProfile(){
		return profileServiceApi.findAll();
	}

}
