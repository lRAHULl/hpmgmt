/**
 * 
 */
package com.rahul.hpmgmt.services.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rahul.hpmgmt.model.Patient;

import static com.rahul.hpmgmt.PatientsArray.PATIENT_ARRAY;
import static com.rahul.hpmgmt.constants.PatientTestConstants.*;

/**
 * @author rahul
 *
 */
public class PatientServicesTest {
	
	PatientServicesImpl patient;
	public static final Logger LOGGER = LoggerFactory.getLogger(PatientServicesImpl.class);
	ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_SOURCE);
	
	@BeforeMethod
	public void setUp() {
		patient = new PatientServicesImpl();
		PATIENT_ARRAY = new HashMap<Integer, Patient>();
	}
	
	@Test
	public void createANewPatientTest() {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0000T));
		try {
			boolean status = patient.createANewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			patient.createANewPatient(ID_TWO, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			patient.createANewPatient(ID_TWO, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			LOGGER.debug(PATIENT_ARRAY.toString());
			LOGGER.debug(HPMT0000D);
			assertTrue(status); 
		} catch (Exception e) {
			String exceptionName = e.getClass().getSimpleName();
			LOGGER.error(HPMT0000E);
			assertEquals(exceptionName, ID_ALREADY_EXISTS_EXCEPTION);
		}
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0001T));
	}
	 
	@Test
	public void readAllPatientsTest() throws Exception {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0002T));
		patient.createANewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE); 
		patient.createANewPatient(ID_TWO, NAME_TWO, AGE_TWO, ADDRESS_TWO);
		Map<Integer, Patient> patients = patient.readAllPatient();
		assertEquals(patients.size(), PATIENT_ARRAY.size());
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0003T));
	}
	
	@Test
	public void updateAnExistingPatientTest() throws Exception {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0004T));
		patient.createANewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE); 
		Patient updatedPatient = patient.updateAnExistingPatient(ID_ONE, NAME_TWO, AGE_TWO, ADDRESS_TWO); 
		assertEquals(updatedPatient.getPatientName(), NAME_TWO);
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0005T));
	}
	
	@Test
	public void deleteAPatient() throws Exception {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0006T));
		patient.createANewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE); 
		patient.createANewPatient(ID_TWO, NAME_TWO, AGE_TWO, ADDRESS_TWO);
		Patient deletedPatient = patient.deleteAPatient(ID_TWO);
		assertEquals(deletedPatient.getPatientName(), NAME_TWO);
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0007T));
	}
}
