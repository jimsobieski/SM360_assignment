package sm360.techassignment.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import sm360.techassignment.dao.DealerDAO;
import sm360.techassignment.entity.Dealer;
import sm360.techassignment.exception.DealerAlreadyExistException;
import sm360.techassignment.exception.EntityNotFoundException;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DealerService {

	private final DealerDAO dealerDAO;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DealerService.class);
	/**
	 * find a dealer by id
	 * 
	 * @param id
	 * @return a dealer
	 */
	public Dealer findById(UUID id) {
		return dealerDAO.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}
	
	/**
	 * get all dealer
	 * 
	 * @return a list of all dealer
	 */
	public List<Dealer> findAll(){
		return dealerDAO.findAll();
	}
	
	/**
	 * create a dealer if name doesn't already exist
	 * 
	 * @param name
	 * @return
	 */
	public Dealer create(String name) {
		if(isDealerNameAlreadyExist(name)) {
			throw new DealerAlreadyExistException();
		}
		var dealer = Dealer.builder().name(name).build();
		LOGGER.debug("Create dealer with name {}", name);
		return dealerDAO.save(dealer);
	}
	
	public boolean isDealerNameAlreadyExist(String name) {
		return dealerDAO.dealerNameAlreadyExist(name);
	}
}
