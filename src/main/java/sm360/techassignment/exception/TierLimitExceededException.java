package sm360.techassignment.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Dealer's tierlimit exceeded")
public class TierLimitExceededException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
