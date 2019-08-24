/**
 * 
 */
package com.rahul.hpmgmt.services.impl;

import com.rahul.hpmgmt.exceptions.IdAlreadyExistsException;
import com.rahul.hpmgmt.exceptions.PatientDirectoryFullException;
import com.rahul.hpmgmt.exceptions.PatientWithIdNotFoundException;
import com.rahul.hpmgmt.model.Patient;
import com.rahul.hpmgmt.services.PatientServices;

import static com.rahul.hpmgmt.PatientsArray.PATIENT_ARRAY;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rahul
 *
 */
public class PatientServicesImpl implements PatientServices {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientServicesImpl.class);
	private static final ResourceBundle MESSAGE_BUNDLER = ResourceBundle.getBundle("messages");
	
	@Override
	public boolean createANewPatient(int id, String name, int age) throws PatientDirectoryFullException, IdAlreadyExistsException {
		
		LOGGER.info("Entered the CreatePatient method");
		Patient newPatient = new Patient();
		LOGGER.info("Created a new patient");
		
		newPatient.setPatientId(id);
		newPatient.setPatientName(name);
		newPatient.setPatientAge(age);
		
		int patientCount = findNumberOfPatients();
		if (patientCount >= PATIENT_ARRAY.length || patientCount < 0) {
			LOGGER.error("Patient array full");
			throw new PatientDirectoryFullException();
		} else if (findIfUserIdExists(id)) {
			LOGGER.error("Patient with Id already exists");
			throw new IdAlreadyExistsException();
		}	else {
			LOGGER.info(Integer.valueOf(patientCount).toString());
			PATIENT_ARRAY[patientCount] = newPatient;
			LOGGER.info("exited the CreatePatient method");
			return true;
		}
	}

	@Override
	public Patient[] readAllPatient() {
		// TODO Auto-generated method stub
		LOGGER.info("In ReadAllPatients method");
		return PATIENT_ARRAY;
	}

	@Override
	public Patient updateAnExistingPatient(int id, String name, int age) throws PatientWithIdNotFoundException {
		// TODO Auto-generated method stub
		Patient patient;
		if (PATIENT_ARRAY.length > 0 && findIfUserIdExists(id)) {
			LOGGER.info("Id Of existing User " + Integer.valueOf(findIndexOfExisting(id)));
			patient = PATIENT_ARRAY[findIndexOfExisting(id)];
			LOGGER.info("Id Of user " + Integer.valueOf(patient.getPatientId()));
			patient.setPatientName(name);
			patient.setPatientAge(age);
			return PATIENT_ARRAY[findIndexOfExisting(id)];
		}
		throw new PatientWithIdNotFoundException();
	}

	@Override
	public Patient deleteAPatient(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean findIfUserIdExists(int id) {
		for (int looper = 0; looper < PATIENT_ARRAY.length  && PATIENT_ARRAY[looper] != null; looper++) {
			if (PATIENT_ARRAY[looper].getPatientId() == id)
				return true;
		}
		return false;
	}
	
	public int findIndexOfExisting(int id) {
		for (int looper = 0; looper < PATIENT_ARRAY.length  && PATIENT_ARRAY[looper] != null; looper++) {
			if (PATIENT_ARRAY[looper].getPatientId() == id)
				return looper;
		}
		return -1;
	}

	@Override
	public int findNumberOfPatients() {
		int count = 0;
		
		for (int looper = 0; looper < PATIENT_ARRAY.length  && PATIENT_ARRAY[looper] != null; looper++) {
			count++;
		}
		return count;
	}

}
