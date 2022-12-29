package sm360.techassignment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import sm360.techassignment.dao.DealerDAO;
import sm360.techassignment.dao.ListingDAO;
import sm360.techassignment.dao.ProfileDAO;
import sm360.techassignment.entity.Dealer;
import sm360.techassignment.entity.Listing;
import sm360.techassignment.entity.Profile;
import sm360.techassignment.enumeration.ListingState;
import sm360.techassignment.enumeration.ProfileEnum;
import sm360.techassignment.exception.DealerAlreadyExistException;
import sm360.techassignment.exception.TierLimitExceededException;
import sm360.techassignment.service.DealerService;

@SpringBootTest
@TestInstance(Lifecycle.PER_METHOD)
public class DealerServiceTests {
	
	private static final String DEALER_NAME_1 = "Toyota";
	private static final String DEALER_NAME_2 = "Renault";
	
	@Mock
	private DealerDAO dealerDAO;
	
	@Mock
	private ProfileDAO profileDAO;
	
	@Mock
	private ListingDAO listingDAO;
	
	@InjectMocks
	private DealerService dealerService;

	@Test
	public void testFindById() {
	    // Given
	    UUID id = UUID.randomUUID();
	    Dealer expectedDealer = Dealer.builder().id(id).name(DEALER_NAME_1).build();
	    given(dealerDAO.findById(id)).willReturn(Optional.of(expectedDealer));

	    // When
	    Dealer actualDealer = dealerService.findById(id);

	    // Then
	    assertThat(actualDealer).isEqualTo(expectedDealer);
	}
	
	@Test
	public void testFindAll() {
	    // Given
	    Dealer dealer1 = Dealer.builder().id(UUID.randomUUID()).name(DEALER_NAME_1).build();
	    Dealer dealer2 = Dealer.builder().id(UUID.randomUUID()).name(DEALER_NAME_2).build();
	    given(dealerDAO.findAll()).willReturn(Arrays.asList(dealer1, dealer2));

	    // When
	    List<Dealer> dealers = dealerService.findAll();

	    // Then
	    assertThat(dealers).containsExactly(dealer1, dealer2);
	}
	
	@Test
	public void testCreateDealerNameAlreadyExists() {
	    // Given
	    when(dealerDAO.dealerNameAlreadyExist(DEALER_NAME_1)).thenReturn(true);

	    // When
	    Throwable exception = catchThrowable(() -> dealerService.create(DEALER_NAME_1));

	    // Then
	    assertThat(exception)
	        .isInstanceOf(DealerAlreadyExistException.class);
	    verify(dealerDAO, never()).save(any(Dealer.class));
	}

	@Test
	public void testCreateDealerNameDoesNotExist() {
	    // Given
		var dealer = Dealer.builder().name(DEALER_NAME_1).id(UUID.randomUUID()).build();
		
	    when(dealerDAO.dealerNameAlreadyExist(DEALER_NAME_1)).thenReturn(false);
	    when(dealerDAO.save(dealer)).thenReturn(dealer);
	    // When
	    dealerService.create(DEALER_NAME_1);

	    // Then
	    verify(dealerDAO).save(any());
	}
	
	@Test
	public void testUpdateProfileSucceed() {
		// Given
		UUID dealerId = UUID.randomUUID();
		
		Profile normalProfile = normalProfile();
		Profile premiumProfile = premiumProfile();
		
		Dealer dealer = Dealer.builder()
				.id(dealerId)
				.name(DEALER_NAME_1)
				.profile(premiumProfile)
				.build();
		
		Dealer dealerUpdated = Dealer.builder()
				.id(dealerId)
				.name(DEALER_NAME_1)
				.profile(normalProfile)
				.build();
		  
		
		  
		List<Listing> mockListingList = Mockito.mock(List.class);
	    when(mockListingList.size()).thenReturn(2);
	    	
		when(dealerDAO.findById(dealerId)).thenReturn(Optional.of(dealer));
		when(profileDAO.findById(ProfileEnum.NORMAL)).thenReturn(Optional.of(normalProfile));
		when(profileDAO.findById(ProfileEnum.PREMIUM)).thenReturn(Optional.of(premiumProfile));
		when(listingDAO.findListingByDealerIdAndState(dealerId, ListingState.published))
			.thenReturn(mockListingList);
		when(dealerDAO.save(any())).thenReturn(dealerUpdated);
		  
		  // When
		  var result = dealerService.updateProfile(dealerId, ProfileEnum.NORMAL);
		  
		  // Then
		  assertEquals(normalProfile, result.getProfile());
		  verify(dealerDAO).save(any(Dealer.class));
	}
	
	@Test
	public void testUpdateProfileFailed() {
		// Given
		UUID dealerId = UUID.randomUUID();
		
		Profile normalProfile = normalProfile();
		Profile premiumProfile = premiumProfile();
		
		Dealer dealer = Dealer.builder()
				.id(dealerId)
				.name(DEALER_NAME_1)
				.profile(premiumProfile)
				.build();
		
		Dealer dealerUpdated = Dealer.builder()
				.id(dealerId)
				.name(DEALER_NAME_1)
				.profile(normalProfile)
				.build();
		  
		
		  
		List<Listing> mockListingList = Mockito.mock(List.class);
	    when(mockListingList.size()).thenReturn(20);
	    	
		when(dealerDAO.findById(dealerId)).thenReturn(Optional.of(dealer));
		when(profileDAO.findById(ProfileEnum.NORMAL)).thenReturn(Optional.of(normalProfile));
		when(profileDAO.findById(ProfileEnum.PREMIUM)).thenReturn(Optional.of(premiumProfile));
		when(listingDAO.findListingByDealerIdAndState(dealerId, ListingState.published))
			.thenReturn(mockListingList);
		when(dealerDAO.save(dealerUpdated)).thenReturn(dealerUpdated);
		  
		  // When
		Throwable exception = catchThrowable(() -> dealerService.updateProfile(dealerId, ProfileEnum.NORMAL));
		  
		  // Then
		  assertThat(exception)
	        .isInstanceOf(TierLimitExceededException.class);
	    verify(dealerDAO, never()).save(any(Dealer.class));
	}
	
	private Profile normalProfile() {
		return Profile.builder()
				.code(ProfileEnum.NORMAL)
				.maxPublications(5)
				.build();
	}
	
	private Profile premiumProfile() {
		return Profile.builder()
				.code(ProfileEnum.PREMIUM)
				.maxPublications(15)
				.build();
	}


}
