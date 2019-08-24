/**
 * 
 */
package com.rahul.hpmgmt.services.impl;

import static com.rahul.hpmgmt.PatientsArray.PATIENT_ARRAY;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rahul.hpmgmt.model.Patient;

/**
 * @author rahul
 *
 */
public class HelperMethodsTest {
	PatientServicesImpl patient;
	public static final Logger LOGGER = LoggerFactory.getLogger(HelperMethodsTest.class);
	
	@BeforeMethod
	public void setUp() {
		patient = new PatientServicesImpl();
		PATIENT_ARRAY = new Patient[10];
	}
	
	@Test
	public void findIfUserIdExistsTest() throws Exception {
		patient.createANewPatient(1, "AAAA", 19);
		boolean status = patient.findIfUserIdExists(1);
		assertTrue(status);
	}
	
	@Test
	public void findIfUserIdNotExistsTest() throws Exception {
		patient.createANewPatient(1, "AAAA", 19);
		boolean status = patient.findIfUserIdExists(2);
		assertFalse(status);
	}
	
	@Test
	public void findIndexOfExistingTest() throws Exception {
		patient.createANewPatient(2, "AAAA", 19);
		patient.createANewPatient(1, "AAAA", 19);
		int actualIndex = patient.findIndexOfExisting(2);
		assertEquals(actualIndex, 0);
	}
	
	@Test
	public void findIndexOfNotExistingTest() throws Exception {
		patient.createANewPatient(2, "AAAA", 19);
		patient.createANewPatient(1, "AAAA", 19);
		int actualIndex = patient.findIndexOfExisting(3);
		assertEquals(actualIndex, -1);
	}
	
	@Test
	public void findNumberOfPatientsForEmptyArrayTest() {
		int actual = patient.findNumberOfPatients();
		assertEquals(actual, 0);
	}
	
	@Test
	public void findNumberOfPatientsForPartialArrayTest() throws Exception {
		
		patient.createANewPatient(1, "AAAA", 19);
		patient.createANewPatient(2, "AAAA", 19);
		patient.createANewPatient(3, "AAAA", 19);
		int actual = patient.findNumberOfPatients();
		assertEquals(actual, 3);
	}
	
	@Test
	public void findNumberOfPatientsForFullArrayTest() throws Exception {
		
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
		int actual = patient.findNumberOfPatients();
		assertEquals(actual, 10);
	}
}
