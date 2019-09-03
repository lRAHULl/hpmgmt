/**
 * 
 */
package com.hospitalmanagement.patient.model;

import java.util.List;

/**
 * @author rahul
 *
 */
public class Patient {
	private int patientId;
	private String patientName;
	private int patientAge;
	
	
	public Patient() {
		super();
	}
	
	public Patient(int patientId, String patientName, int patientAge, List<String> patientAddress) {
		super();
		this.patientId = patientId;
		this.patientName = patientName;
		this.patientAge = patientAge;
		this.patientAddress = patientAddress;
	}

	private List<String> patientAddress;
	
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public int getPatientAge() {
		return patientAge;
	}
	public void setPatientAge(int patientAge) {
		this.patientAge = patientAge;
	}
	public List<String> getPatientAddress() {
		return patientAddress;
	}
	public void setPatientAddress(List<String> patientAddress) {
		this.patientAddress = patientAddress;
	}
	
	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", patientName=" + patientName + ", patientAge=" + patientAge
				+ ", patientAddress=" + patientAddress + "]";
	}
	
}
