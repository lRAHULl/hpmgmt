/**
 * 
 */
package com.hospitalmanagement.patient.exceptions;

/**
 * @author VC
 *
 */
public class NoUserExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -404257884737103666L;

	/**
	 * 
	 */
	public NoUserExistsException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public NoUserExistsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public NoUserExistsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoUserExistsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NoUserExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
