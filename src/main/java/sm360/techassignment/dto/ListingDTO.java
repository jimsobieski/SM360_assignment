package sm360.techassignment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ListingDTO {

	private UUID id;
	private UUID dealerId;
	private String vehicle;
	private BigDecimal price;
	private LocalDate createdAt;
	private Boolean isPublished; 
}
