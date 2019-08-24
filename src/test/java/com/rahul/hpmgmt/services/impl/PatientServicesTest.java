/**
 * 
 */
package com.rahul.hpmgmt.services.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rahul.hpmgmt.exceptions.PatientWithIdNotFoundException;
import com.rahul.hpmgmt.model.Patient;

import static com.rahul.hpmgmt.PatientsArray.PATIENT_ARRAY;

/**
 * @author rahul
 *
 */
public class PatientServicesTest {
	
	PatientServicesImpl patient;
	public static final Logger LOGGER = LoggerFactory.getLogger(PatientServicesImpl.class);
	
	@BeforeMethod
	public void setUp() {
		patient = new PatientServicesImpl();
		PATIENT_ARRAY = new Patient[10];
	}
	
	@Test
	public void createANewPatientTest() {
		
		try {
			boolean status = patient.createANewPatient(1, "ABCD", 19);
//			patient.createANewPatient(1, "ABCD", 19);
			assertTrue(status);
		} catch (Exception e) {
			String exceptionName = e.getClass().getSimpleName();
			assertEquals(exceptionName, "IdAlreadyExistsException");
		}
	}
	
	@Test
	public void readAllPatientsTest() throws Exception {
		patient.createANewPatient(1, "AAAA", 19);
		patient.createANewPatient(2, "AAAA", 19);
		patient.createANewPatient(3, "AAAA", 19);
		assertEquals(patient.findNumberOfPatients(), 3);
	}
	
	@Test
	public void updateAnExistingPatientTest() throws Exception {
		patient.createANewPatient(1, "AAAA", 19);
		LOGGER.info("Name: " + PATIENT_ARRAY[0].getPatientName());
		Patient updatedPatient = patient.updateAnExistingPatient(1, "BBBB", 20);
		assertEquals(updatedPatient.getPatientName(), "BBBB");
	}
}
