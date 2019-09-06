/**
 * 
 */
package com.hospitalmanagement.patient.dao.impl;

import static com.hospitalmanagement.patient.constants.PatientDAOConstants.*;
import static com.hospitalmanagement.patient.constants.PatientTestConstants.*;
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
import com.hospitalmanagement.patient.exceptions.FileInputOutputException;
import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientWithIdNotFoundException;
import com.hospitalmanagement.patient.model.Patient;

/**
 * @author VC
 *
 */
public class PatientJSONDAOTest {
	PatientDAO patientDAO;
	public static final Logger LOGGER = LoggerFactory.getLogger(PatientJSONDAOTest.class);
	ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_SOURCE);
	String tempFilePath = FILE_PATH;
	
	
	@BeforeClass
	public void testSetUp() {
		FILE_PATH = JSON_TEST_FILE_PATH;
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
	public void createPatientTest() {
		Patient patient = new Patient();
		patient.setPatientId(1);
		patient.setPatientName("ABCD");
		patient.setPatientAge(19);
		patient.setPatientAddress(Arrays.asList("NYC", "NY", "USA"));
		boolean test;
		try {
			test = patientDAO.createPatient(patient);
			assertTrue(test);
		} catch (IdAlreadyExistsException e) {

		} catch (FileInputOutputException e) {

		} catch (IOException e) {

		}
		
	}
	
	@Test
	public void readPatientsTest() {
		Patient patient = new Patient();
		patient.setPatientId(1);
		patient.setPatientName("ABCD");
		patient.setPatientAge(19);
		patient.setPatientAddress(Arrays.asList("NYC", "NY", "USA"));
		try {
			patientDAO.createPatient(patient);
		} catch (FileInputOutputException e) {
			
		} catch (IOException e) {
			
		} catch (IdAlreadyExistsException e) {

		}
		List<Patient> readResult;
		try {
			readResult = patientDAO.readPatients();
			assertEquals(readResult.size(), 1);
		} catch (FileInputOutputException e) {
			
		} catch (IOException e) {

		} catch (NoUserExistsException e) {

		} 
		
	}
	
	@Test
	public void updatePatient() {
		Patient p = new Patient();
		Patient patient = new Patient();
		patient.setPatientId(1);
		patient.setPatientName("ABCD");
		patient.setPatientAge(19);
		patient.setPatientAddress(Arrays.asList("NYC", "NY", "USA"));
		
		try {
			patientDAO.createPatient(patient);
		} catch (IOException | IdAlreadyExistsException | FileInputOutputException e1) {
			LOGGER.debug("Create patient failed");
		}
		
		p.setPatientId(1);
		p.setPatientName("BBBB");
		p.setPatientAge(22);
		p.setPatientAddress(Arrays.asList("SF", "CA", "USA"));
		
		Patient updated;
		try {
			updated = patientDAO.updatePatient(1, p);
			assertEquals(updated.getPatientName(), p.getPatientName());
		} catch (IOException e) {

		} catch (PatientWithIdNotFoundException e) {

		} catch (NoUserExistsException e) {

		} catch (FileInputOutputException e) {

		}
			
		
	}
	
	@Test
	public void deletePatient() {
		Patient patient = new Patient();
		patient.setPatientId(1);
		patient.setPatientName("ABCD");
		patient.setPatientAge(19);
		patient.setPatientAddress(Arrays.asList("NYC", "NY", "USA"));
		try {
			patientDAO.createPatient(patient);
		} catch (FileInputOutputException e) {
			
		} catch (IOException e) {

		} catch (IdAlreadyExistsException e) {

		}
		Patient p;
		try {
			p = patientDAO.deletePatient(1);
			assertEquals(p.getPatientName(), "ABCD");
		} catch (PatientWithIdNotFoundException e) {

		} catch (NoUserExistsException e) {

		} catch (FileInputOutputException e) {

		} catch (IOException e) {

		}
		
	}
}
