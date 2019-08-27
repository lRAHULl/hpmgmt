/**
 * 
 */
package com.hospitalmanagement.patient.exceptions;

/**
 * @author rahul
 *
 */
public class PatientDirectoryFullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7306148603840843259L;

	/**
	 * 
	 */
	public PatientDirectoryFullException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public PatientDirectoryFullException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public PatientDirectoryFullException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PatientDirectoryFullException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PatientDirectoryFullException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
