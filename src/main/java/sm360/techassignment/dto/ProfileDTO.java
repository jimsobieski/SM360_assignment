package sm360.techassignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProfileDTO {

	private String code;
	private int maxPublications;
}
