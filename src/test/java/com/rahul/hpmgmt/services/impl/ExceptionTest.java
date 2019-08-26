/**
 * 
 */
package com.rahul.hpmgmt.services.impl;

import static com.rahul.hpmgmt.PatientsArray.PATIENT_ARRAY;
import static com.rahul.hpmgmt.constants.PatientTestConstants.*;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

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
	public static final Logger LOGGER = LoggerFactory.getLogger(Exception.class);
	
	@BeforeMethod
	public void setUp() {
		patient = new PatientServicesImpl();
		PATIENT_ARRAY = new HashMap<Integer, Patient>();
	}
	
	@Test
	public void idAlreadyExistsExceptionTest() {
		try {
			patient.createANewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			patient.createANewPatient(ID_ONE, NAME_TWO, AGE_TWO, ADDRESS_TWO);
		} catch (Exception e) {
			String actualExceptionName = e.getClass().getSimpleName();
			assertEquals(actualExceptionName, ID_ALREADY_EXISTS_EXCEPTION);
		}
	}
	
	@Test
	public void patientWithIdNotFoundExceptionTest() {
		try {
			patient.createANewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			patient.updateAnExistingPatient(ID_TWO, NAME_TWO, AGE_TWO, ADDRESS_TWO);
		} catch (Exception e) {
			String actualExceptionName = e.getClass().getSimpleName();
			assertEquals(actualExceptionName, PATIENT_WITH_ID_NOT_FOUND_EXCEPTION);
		}
	}
}
