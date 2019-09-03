/**
 * 
 */
package com.hospitalmanagement.patient.services.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hospitalmanagement.patient.model.Patient;
import com.hospitalmanagement.patient.services.impl.PatientServicesImpl;

import static com.hospitalmanagement.patient.constants.PatientDAOConstants.FILE_PATH;
import static com.hospitalmanagement.patient.constants.PatientDAOConstants.TEST_FILE_PATH;
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
	String tempFilePath = FILE_PATH;
	
	@BeforeClass
	public void testSetUp() {
		FILE_PATH = TEST_FILE_PATH;
	}
	
	@AfterClass
	public void reset() {
		FILE_PATH = tempFilePath;
	}
	
	@BeforeMethod
	public void setUp() {
		patient = new PatientServicesImpl();
		File file = new File(FILE_PATH);
		file.delete();
	}
	@AfterMethod
	public void flushUp() throws IOException {
		File file = new File(FILE_PATH);
		file.delete();
		file.createNewFile();
	}
	
	@Test
	public void createANewPatientTest() {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0000T));
		try {
			boolean status = patient.createNewPatient(PATIENT_ONE);
			patient.createNewPatient(PATIENT_TWO);
			patient.createNewPatient(PATIENT_THREE);
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
		patient.createNewPatient(PATIENT_ONE); 
		patient.createNewPatient(PATIENT_TWO);
		try {
			List<Patient> patients = patient.readAllPatient();
			assertEquals(patients.size(), 2);
		} catch (Exception e) {
			e.getClass().getSimpleName().equals(NO_USER_EXISTS_EXCEPTION);
		}
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0003T));
	}
	
	@Test
	public void updateAnExistingPatientTest() throws Exception {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0004T));
		patient.createNewPatient(PATIENT_ONE); 
		Patient updatedPatient = patient.updateExistingPatient(UPDATED_PATIENT_ONE); 
		assertEquals(updatedPatient.getPatientName(), PATIENT_TWO.getPatientName());
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0005T));
	}
	
	@Test
	public void deleteAPatient() throws Exception {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0006T));
		patient.createNewPatient(PATIENT_ONE); 
		patient.createNewPatient(PATIENT_TWO);
		Patient deletedPatient = patient.deletePatient(PATIENT_TWO.getPatientId());
		assertEquals(deletedPatient.getPatientName(), PATIENT_TWO.getPatientName());
		LOGGER.info(MESSAGE_BUNDLE.getString(HPMT0007T));
	}
}
