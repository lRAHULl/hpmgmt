/**
 * 
 */
package com.rahul.hpmgmt.model;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.rahul.hpmgmt.constants.PatientConstants.LOGGER_MESSAGES;

/**
 * @author rahul
 *
 */
public class Patient {
	private int patientId;
	private String patientName;
	private int patientAge;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Patient.class);
	private static final ResourceBundle MESSAGE_BUNDLER = ResourceBundle.getBundle(LOGGER_MESSAGES);
	
	public int getPatientId() {
		LOGGER.info("invoked the getPatientId method");
		return patientId;
	}
	public void setPatientId(int patientId) {
		LOGGER.info("invoked the setPatientId method");
		this.patientId = patientId;
	}
	public String getPatientName() {
		LOGGER.info("invoked the getPatientName method");
		return patientName;
	}
	public void setPatientName(String patientName) {
		LOGGER.info("invoked the setPatientName method");
		this.patientName = patientName;
	}
	public int getPatientAge() {
		LOGGER.info("invoked the getPatientAge method");
		return patientAge;
	}
	public void setPatientAge(int patientAge) {
		LOGGER.info("invoked the setPatientAge method");
		this.patientAge = patientAge;
	}
	
	@Override
	public String toString() {
		LOGGER.info("Invoked the toString method in Patient model");
		return "Patient [patientId=" + patientId + ", patientName=" + patientName + ", patientAge=" + patientAge + "]";
	}
	
}
