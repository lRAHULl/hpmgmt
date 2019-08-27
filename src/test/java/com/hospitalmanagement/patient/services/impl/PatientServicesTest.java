/**
 * 
 */
package com.hospitalmanagement.patient.services.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hospitalmanagement.patient.model.Patient;
import com.hospitalmanagement.patient.services.impl.PatientServicesImpl;

import static com.hospitalmanagement.patient.PatientsArray.PATIENT_COLLECTION;
import static com.hospitalmanagement.patient.constants.PatientTestConstants.*;

/**
 * @author rahul
 * Test class for PatientServicesImpl Class
 * It tests all four 
 */
public class PatientServicesTest {
	
	PatientServicesImpl patient;
	public static final Logger LOGGER = LoggerFactory.getLogger(PatientServicesImpl.class);
	ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_SOURCE);
	
	@BeforeMethod
	public void setUp() {
		patient = new PatientServicesImpl();
		PATIENT_COLLECTION = new HashMap<Integer, Patient>();
	}
	
	@Test
	public void createANewPatientTest() {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0000T));
		try {
			boolean status = patient.createNewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			patient.createNewPatient(ID_TWO, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			patient.createNewPatient(ID_TWO, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			LOGGER.debug(PATIENT_COLLECTION.toString());
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
	public void createANewPatientFailProofTest() {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0000T));
		try {
			boolean status = patient.createNewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			patient.createNewPatient(ID_TWO, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			patient.createNewPatient(ID_FALSE, NAME_ONE, AGE_ONE, ADDRESS_ONE);
			LOGGER.debug(PATIENT_COLLECTION.toString());
			LOGGER.debug(HPMT0000D);
			assertTrue(status); 
		} catch (Exception e) {
			String exceptionName = e.getClass().getSimpleName();
			LOGGER.error(HPMT0000E);
			assertEquals(exceptionName, INPUT_CONSTRAINT_NOT_AS_EXPECTED_EXCEPTION);
		}
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0001T));
	}
	 
	@Test
	public void readAllPatientsTest() throws Exception {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0002T));
		patient.createNewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE); 
		patient.createNewPatient(ID_TWO, NAME_TWO, AGE_TWO, ADDRESS_TWO);
		try {
			Map<Integer, Patient> patients = patient.readAllPatient();
			assertEquals(patients.size(), PATIENT_COLLECTION.size());
		} catch (Exception e) {
			e.getClass().getSimpleName().equals(NO_USER_EXISTS_EXCEPTION);
		}
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0003T));
	}
	
	@Test
	public void updateAnExistingPatientTest() throws Exception {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0004T));
		patient.createNewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE); 
		Patient updatedPatient = patient.updateExistingPatient(ID_ONE, NAME_TWO, AGE_TWO, ADDRESS_TWO); 
		assertEquals(updatedPatient.getPatientName(), NAME_TWO);
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0005T));
	}
	
	@Test
	public void deleteAPatient() throws Exception {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0006T));
		patient.createNewPatient(ID_ONE, NAME_ONE, AGE_ONE, ADDRESS_ONE); 
		patient.createNewPatient(ID_TWO, NAME_TWO, AGE_TWO, ADDRESS_TWO);
		Patient deletedPatient = patient.deletePatient(ID_TWO);
		assertEquals(deletedPatient.getPatientName(), NAME_TWO);
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0007T));
	}
}
