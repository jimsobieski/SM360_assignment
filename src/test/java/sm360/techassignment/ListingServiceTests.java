package sm360.techassignment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import sm360.techassignment.dao.ListingDAO;
import sm360.techassignment.entity.Dealer;
import sm360.techassignment.entity.Listing;
import sm360.techassignment.enumeration.ListingState;
import sm360.techassignment.exception.TierLimitExceededException;
import sm360.techassignment.mapper.ListingMapper;
import sm360.techassignment.properties.Sm360Properties;
import sm360.techassignment.service.ListingService;

@SpringBootTest
@TestInstance(Lifecycle.PER_METHOD)
public class ListingServiceTests {
    private static final UUID DEALER_ID_1 = UUID.randomUUID();
    private static final UUID DEALER_ID_2 = UUID.randomUUID();
    private static final String VEHICULE_NAME = "Toyota Camry"; 

    @Mock
    private ListingDAO listingDAO;
    
    @Mock
    private Sm360Properties sm360Properties;

    @InjectMocks
    private ListingService listingService;
    
    @Test
    public void testCreateListing() {
        // given
        var listing = getDraftListing();
        
        given(listingDAO.save(any(Listing.class))).willReturn(listing);

        // when
        var result = listingService.create(listing);

        // then
        assertThat(result).isEqualTo(listing);
        assertThat(result.getState()).isEqualTo(ListingState.draft);
        assertThat(result.getCreatedAt()).isToday();
    }
    
    @Test
    public void testFindListingByDealerIdAndState() {
    	// given
        var listing1 = Listing.builder().id(UUID.randomUUID()).state(ListingState.published)
        		.dealer(Dealer.builder().id(DEALER_ID_1).build()).build();
        var listing2 = Listing.builder().id(UUID.randomUUID()).state(ListingState.published)
        		.dealer(Dealer.builder().id(DEALER_ID_2).build()).build();

        given(listingDAO.findListingByDealerIdAndState(DEALER_ID_1, ListingState.published)).willReturn(Arrays.asList(listing1));
        given(listingDAO.findListingByDealerIdAndState(DEALER_ID_2, ListingState.published)).willReturn(Arrays.asList(listing2));
        // when
        var result1 = listingService.findListingByDealerIdAndState(DEALER_ID_1, ListingState.published);
        var result2 = listingService.findListingByDealerIdAndState(DEALER_ID_2, ListingState.published);

        // then
        assertThat(result1).containsExactly(listing1);
        assertThat(result2).containsExactly(listing2);
    }
    
    @Test
    public void testPublishWithTierLimitExceeded() {
    	// given
    	var listing = getDraftListing();
    	
    	List<Listing> mockListingList = Mockito.mock(List.class);
    	Mockito.when(mockListingList.size()).thenReturn(5);
    	
    	given(listingDAO.findById(listing.getId())).willReturn(Optional.of(listing));
    	given(listingDAO.findListingByDealerIdAndState(DEALER_ID_1, ListingState.published))
    	.willReturn(mockListingList);
    	given(sm360Properties.getTierlimit()).willReturn(5);

    	// when
    	assertThrows(TierLimitExceededException.class, () -> listingService.publish(listing.getId(), false));

    	// then
    	verify(listingDAO, Mockito.never()).setStateOldestPublishListingByDealerId(DEALER_ID_1, ListingState.draft);
    	
    	
    }
    
    @Test
    public void testPublishWithOverwrite() {
    	// given
    	var draftListing = getDraftListing();
    	var publishListing = getPublishListing();
    	List<Listing> mockListingList = Mockito.mock(List.class);
    	Mockito.when(mockListingList.size()).thenReturn(5);
    	
    	given(listingDAO.findById(draftListing.getId())).willReturn(Optional.of(draftListing));
    	given(listingDAO.findListingByDealerIdAndState(DEALER_ID_1, ListingState.published))
    	.willReturn(mockListingList);
    	given(listingDAO.save(draftListing)).willReturn(publishListing);
    	given(sm360Properties.getTierlimit()).willReturn(5);
    	// when
    	var result = listingService.publish(draftListing.getId(), true);

    	// then
    	assertThat(result.getState()).isEqualTo(ListingState.published);
    	verify(listingDAO).setStateOldestPublishListingByDealerId(DEALER_ID_1, ListingState.draft);
    }
    
    @Test
    public void testUnpublish() {
        // given
        UUID listingId = UUID.randomUUID();
        Listing publishedListing = getPublishListing();
        given(listingDAO.findById(listingId)).willReturn(Optional.of(publishedListing));
        given(listingDAO.save(publishedListing)).willReturn(publishedListing);

        // when
        Listing result = listingService.unpublish(listingId);

        // then
        assertThat(result.getState()).isEqualTo(ListingState.draft);
        verify(listingDAO).findById(listingId);
        verify(listingDAO).save(publishedListing);
    }

    
    @Test
    public void testMapEntityToDTO() {
        // given
    	var publishListing = getPublishListing();
    	var draftListing = getDraftListing();
    	
        LocalDate createdAt = publishListing.getCreatedAt();
        BigDecimal price =publishListing.getPrice();
        String vehicle = publishListing.getVehicle();

        // when
        var publishDto = ListingMapper.mapEntityToDTO(publishListing);
        var draftDto = ListingMapper.mapEntityToDTO(draftListing);

        // then
        assertThat(publishDto.getDealerId()).isEqualTo(DEALER_ID_1);
        assertThat(publishDto.getCreatedAt()).isEqualTo(createdAt);
        assertThat(publishDto.getPrice()).isEqualTo(price);
        assertThat(publishDto.getIsPublished()).isTrue();
        assertThat(publishDto.getVehicle()).isEqualTo(vehicle);
        assertThat(draftDto.getIsPublished()).isFalse();
    }
    
    private Listing getDraftListing() {
    	return Listing.builder()
    	    	.id(UUID.randomUUID())
    	    	.dealer(Dealer.builder().id(DEALER_ID_1).build())
    	    	.vehicle(VEHICULE_NAME)
    	    	.price(BigDecimal.valueOf(10000))
    	    	.state(ListingState.draft)
    	    	.build();
    }
    
    private Listing getPublishListing() {
    	return Listing.builder()
    	    	.id(UUID.randomUUID())
    	    	.dealer(Dealer.builder().id(DEALER_ID_1).build())
    	    	.vehicle(VEHICULE_NAME)
    	    	.price(BigDecimal.valueOf(10000))
    	    	.state(ListingState.published)
    	    	.build();
    }

    
}

