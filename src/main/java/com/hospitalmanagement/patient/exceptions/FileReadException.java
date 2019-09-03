/**
 * 
 */
package com.hospitalmanagement.patient.exceptions;

/**
 * @author VC
 *
 */
public class FileReadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4651922356705718969L;

	/**
	 * 
	 */
	public FileReadException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public FileReadException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public FileReadException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FileReadException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public FileReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
