/**
 * 
 */
package com.hospitalmanagement.patient.dao.impl;

import static com.hospitalmanagement.patient.constants.PatientTestConstants.MESSAGES_SOURCE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hospitalmanagement.patient.dao.PatientDAO;
import com.hospitalmanagement.patient.exceptions.FileReadException;
import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientWithIdNotFoundException;
import com.hospitalmanagement.patient.model.Patient;
import static com.hospitalmanagement.patient.constants.PatientDAOConstants.*;


/**
 * @author rahul
 *
 */
public class PatientDAOTest {

	
	PatientDAO patientDAO;
	public static final Logger LOGGER = LoggerFactory.getLogger(PatientDAOTest.class);
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
		patientDAO = new PatientCSVDAOImpl();
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
	public void createPatientTest() throws IOException {
		Patient patient = new Patient();
		patient.setPatientId(1);
		patient.setPatientName("ABCD");
		patient.setPatientAge(19);
		patient.setPatientAddress(Arrays.asList("NYC", "NY", "USA"));
		boolean test;
		try {
			test = patientDAO.createPatient(patient);
			assertTrue(test);
		} catch (Exception e) {
			String exceptionName = e.getClass().getSimpleName();
			assertEquals(exceptionName, "IdAlreadyExistsException");
		}
	}
	
	@Test
	public void readPatientsTest() throws IOException, NoUserExistsException, IdAlreadyExistsException, FileReadException {
		Patient patient = new Patient();
		patient.setPatientId(1);
		patient.setPatientName("ABCD");
		patient.setPatientAge(19);
		patient.setPatientAddress(Arrays.asList("NYC", "NY", "USA"));
		patientDAO.createPatient(patient);
		List<Patient> readResult = patientDAO.readPatients();
		assertEquals(readResult.size(), 1);
	}
	
	@Test
	public void updatePatient() throws IOException, IdAlreadyExistsException, FileReadException {
		Patient p = new Patient();
		Patient patient = new Patient();
		patient.setPatientId(1);
		patient.setPatientName("ABCD");
		patient.setPatientAge(19);
		patient.setPatientAddress(Arrays.asList("NYC", "NY", "USA"));
		patientDAO.createPatient(patient);
		p.setPatientId(1);
		p.setPatientName("BBBB");
		p.setPatientAge(22);
		p.setPatientAddress(Arrays.asList("SF", "CA", "USA"));
		try {
			Patient updated = patientDAO.updatePatient(1, p);
			assertEquals(updated.getPatientName(), p.getPatientName());
		} catch (IOException | PatientWithIdNotFoundException | NoUserExistsException e) {
			String exceptionName = e.getClass().getSimpleName();
			if (exceptionName.equals("NoUserExistsException")) {
				assertEquals(e, "NoUserExistsException");
			} else if (exceptionName.equals("PatientWithIdNotFoundException")) {
				assertEquals(e, "PatientWithIdNotFoundException");
			}
		}
	}
	
	@Test
	public void deletePatient() throws IOException, IdAlreadyExistsException, FileReadException {
		Patient patient = new Patient();
		patient.setPatientId(1);
		patient.setPatientName("ABCD");
		patient.setPatientAge(19);
		patient.setPatientAddress(Arrays.asList("NYC", "NY", "USA"));
		patientDAO.createPatient(patient);
		try {
			Patient p = patientDAO.deletePatient(1);
			assertEquals(p.getPatientName(), "ABCD");
		} catch (IOException | PatientWithIdNotFoundException | NoUserExistsException e) {
			
		}
	}
	
}
