package sm360.techassignment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import sm360.techassignment.dao.ListingDAO;
import sm360.techassignment.entity.Dealer;
import sm360.techassignment.entity.Listing;
import sm360.techassignment.enumeration.ListingState;
import sm360.techassignment.service.ListingService;

@SpringBootTest
@TestInstance(Lifecycle.PER_METHOD)
public class ListingServiceTests {
    private static final UUID DEALER_ID_1 = UUID.randomUUID();
    private static final UUID DEALER_ID_2 = UUID.randomUUID();

    @Mock
    private ListingDAO listingDAO;

    @InjectMocks
    private ListingService listingService;
    
    @Test
    public void testCreateListing() {
        // given
        var listing = Listing.builder()
                .vehicle("Toyota Camry")
                .price(BigDecimal.valueOf(10000))
                .dealer(Dealer.builder().id(DEALER_ID_1).build())
                .build();
        
        given(listingDAO.save(any(Listing.class))).willReturn(listing);

        // when
        var result = listingService.createListing(listing);

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
    
}

