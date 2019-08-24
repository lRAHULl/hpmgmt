/**
 * 
 */
package com.rahul.hpmgmt.services.impl;

import static com.rahul.hpmgmt.PatientsArray.PATIENT_ARRAY;
import static org.testng.Assert.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rahul.hpmgmt.model.Patient;

/**
 * @author rahul
 *
 */
public class ExceptionTest {
	PatientServicesImpl patient;
	public static final Logger LOGGER = LoggerFactory.getLogger(HelperMethodsTest.class);
	
	@BeforeMethod
	public void setUp() {
		patient = new PatientServicesImpl();
//		PATIENT_ARRAY = new Patient[10];
	}
	
	@Test
	public void patientDirectoryFullExceptionTest() {
		try {
			patient.createANewPatient(1, "AAAA", 19); 
			patient.createANewPatient(2, "AAAA", 19);
			patient.createANewPatient(3, "AAAA", 19);
			patient.createANewPatient(4, "AAAA", 19);
			patient.createANewPatient(5, "AAAA", 19);
			patient.createANewPatient(6, "AAAA", 19);
			patient.createANewPatient(7, "AAAA", 19);
			patient.createANewPatient(8, "AAAA", 19);
			patient.createANewPatient(9, "AAAA", 19); 
			patient.createANewPatient(10, "AAAA", 19);
			patient.createANewPatient(11, "AAAA", 19);
		} catch (Exception e) {
			// TODO: handle exception
			String actualExceptionName = e.getClass().getSimpleName();
			assertEquals(actualExceptionName, "PatientDirectoryFullException");
		}
	}
	
	@Test
	public void idAlreadyExistsExceptionTest() {
		try {
			patient.createANewPatient(1, "name", 19);
			patient.createANewPatient(1, "aaaa", 12);
		} catch (Exception e) {
			String actualExceptionName = e.getClass().getSimpleName();
			assertEquals(actualExceptionName, "IdAlreadyExistsException");
		}
	}
	
	@Test
	public void patientWithIdNotFoundExceptionTest() {
		try {
			patient.createANewPatient(1, "name", 19);
			patient.updateAnExistingPatient(2, "bbbb", 15);
		} catch (Exception e) {
			String actualExceptionName = e.getClass().getSimpleName();
			assertEquals(actualExceptionName, "PatientWithIdNotFoundException");
		}
	}
}
