/**
 * 
 */
package com.rahul.hpmgmt.services.impl;

import com.rahul.hpmgmt.exceptions.IdAlreadyExistsException;
import com.rahul.hpmgmt.exceptions.NoUserExistsException;
import com.rahul.hpmgmt.exceptions.PatientDirectoryFullException;
import com.rahul.hpmgmt.exceptions.PatientWithIdNotFoundException;
import com.rahul.hpmgmt.model.Patient;
import com.rahul.hpmgmt.services.PatientServices;

import static com.rahul.hpmgmt.PatientsArray.PATIENT_COLLECTION;
import static com.rahul.hpmgmt.constants.PatientConstants.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rahul
 *
 */
public class PatientServicesImpl implements PatientServices {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientServicesImpl.class);
	private static final ResourceBundle MESSAGE_BUNDLER = ResourceBundle.getBundle(LOGGER_MESSAGES);
	
	@Override
	public boolean createANewPatient(int id, String name, int age, List<String> address) throws PatientDirectoryFullException, IdAlreadyExistsException {
		
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0000T));
		Patient newPatient = new Patient();
		LOGGER.info("Created a new patient object");
		
		newPatient.setPatientId(id);
		newPatient.setPatientName(name);
		newPatient.setPatientAge(age);
		newPatient.setPatientAddress(address);
		
		if (PATIENT_COLLECTION.containsKey(id)) {
			LOGGER.error("Patient with Id already exists");
			throw new IdAlreadyExistsException();
		} else {
			PATIENT_COLLECTION.putIfAbsent(newPatient.getPatientId(), newPatient);
			LOGGER.info(MESSAGE_BUNDLER.getString(HPM0001T));
			return true;
		}
	}

	@Override
	public Map<Integer, Patient> readAllPatient() throws NoUserExistsException {
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0002T));
		if (PATIENT_COLLECTION.size() == 0) {
			LOGGER.info(MESSAGE_BUNDLER.getString(HPM0003T));
			return PATIENT_COLLECTION;
		} else {
			throw new NoUserExistsException();
		}
	}

	/**
	 * 
	 */
	@Override
	public Patient updateAnExistingPatient(int id, String name, int age, List<String> address) throws PatientWithIdNotFoundException {
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0004T));
		Patient patient;
		if (PATIENT_COLLECTION.containsKey(id)) {
			LOGGER.info("Id Of existing User " + PATIENT_COLLECTION.get(id));
			patient = PATIENT_COLLECTION.get(id);
			LOGGER.info("Id Of user " + Integer.valueOf(patient.getPatientId()));
			patient.setPatientName(name);
			patient.setPatientAge(age);
			patient.setPatientAddress(address);
			LOGGER.info(MESSAGE_BUNDLER.getString(HPM0005T));
			return PATIENT_COLLECTION.get(id);
		}
		throw new PatientWithIdNotFoundException();
	}

	@Override
	public Patient deleteAPatient(int id) throws PatientWithIdNotFoundException {
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0006T));
		if (PATIENT_COLLECTION.containsKey(id)) {
			Patient deletedPatient =  PATIENT_COLLECTION.remove(id);
			LOGGER.info(MESSAGE_BUNDLER.getString(HPM0007T));
			return deletedPatient;
		} else {
			throw new PatientWithIdNotFoundException();
		}
	}
	
	@Override
	public int findNumberOfPatients() {
		int count = 0;
		
		Iterator<Integer> it = PATIENT_COLLECTION.keySet().iterator();
		while(it.hasNext()) {
			count++;
		}
		return count;
	}
}
