package sm360.techassignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import sm360.techassignment.entity.Profile;
import sm360.techassignment.enumeration.ProfileEnum;

public interface ProfileDAO extends JpaRepository<Profile, ProfileEnum> {
}

