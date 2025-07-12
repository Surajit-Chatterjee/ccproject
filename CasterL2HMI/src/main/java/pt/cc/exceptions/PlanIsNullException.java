package pt.cc.exceptions;

public class PlanIsNullException extends RuntimeException {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlanIsNullException() {	
	}

	public PlanIsNullException(String message) {
		super(message);	
	}

	public PlanIsNullException(Throwable cause) {
		super(cause);		
	}

	public PlanIsNullException(String message, Throwable cause) {
		super(message, cause);		
	}

	public PlanIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);		
	}

}
