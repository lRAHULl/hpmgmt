/**
 * 
 */
package com.hospitalmanagement.patient.services.impl;

import static com.hospitalmanagement.patient.PatientsArray.PATIENT_COLLECTION;
import static com.hospitalmanagement.patient.constants.PatientTestConstants.*;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hospitalmanagement.patient.model.Patient;
import com.hospitalmanagement.patient.services.impl.PatientServicesImpl;

/**
 * @author rahul
 *  
 *
 */
public class ExceptionTest {
	PatientServicesImpl patient;
	public static final Logger LOGGER = LoggerFactory.getLogger(Exception.class);
	
	@BeforeMethod
	public void setUp() {
		patient = new PatientServicesImpl();
		PATIENT_COLLECTION = new HashMap<Integer, Patient>();
	}
	
	@Test
	public void idAlreadyExistsExceptionTest() {
		try {
			patient.createNewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			patient.createNewPatient(ID_ONE, NAME_TWO, AGE_TWO, ADDRESS_TWO);
		} catch (Exception e) {
			String actualExceptionName = e.getClass().getSimpleName();
			assertEquals(actualExceptionName, ID_ALREADY_EXISTS_EXCEPTION);
		}
	}
	
	@Test
	public void patientWithIdNotFoundExceptionTest() {
		try {
			patient.createNewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			patient.updateExistingPatient(ID_TWO, NAME_TWO, AGE_TWO, ADDRESS_TWO);
		} catch (Exception e) {
			String actualExceptionName = e.getClass().getSimpleName();
			assertEquals(actualExceptionName, PATIENT_WITH_ID_NOT_FOUND_EXCEPTION);
		}
	}
}
