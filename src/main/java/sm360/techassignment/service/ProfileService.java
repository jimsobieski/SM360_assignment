package sm360.techassignment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import sm360.techassignment.dao.ProfileDAO;
import sm360.techassignment.entity.Profile;
import sm360.techassignment.enumeration.ProfileEnum;
import sm360.techassignment.exception.EntityNotFoundException;

@Service
@Transactional
@AllArgsConstructor
public class ProfileService {

    private final ProfileDAO profileDAO;

    /**
     * find a profile by code
     * 
     * @param code
     * @return a profile
     */
    public Profile findById(ProfileEnum code) {
        return profileDAO.findById(code).orElseThrow(() -> new EntityNotFoundException());
    }

    /**
     * list all profiles
     * 
     * @return
     */
    public List<Profile> findAll(){
    	return profileDAO.findAll();
    }
}

