package sm360.techassignment.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DealerDTO {

	private UUID id;
	private String name;
}
