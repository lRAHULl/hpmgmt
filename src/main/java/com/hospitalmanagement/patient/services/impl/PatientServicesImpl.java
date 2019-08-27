/**
 * 
 */
package com.hospitalmanagement.patient.services.impl;

import static com.hospitalmanagement.patient.PatientsArray.PATIENT_COLLECTION;
import static com.hospitalmanagement.patient.constants.PatientConstants.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.InputConstraintNotAsExceptedException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientDirectoryFullException;
import com.hospitalmanagement.patient.exceptions.PatientWithIdNotFoundException;
import com.hospitalmanagement.patient.model.Patient;
import com.hospitalmanagement.patient.services.PatientServices;

/**
 * @author rahul
 *
 */
public class PatientServicesImpl implements PatientServices {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientServicesImpl.class);
	private static final ResourceBundle MESSAGE_BUNDLER = ResourceBundle.getBundle(LOGGER_MESSAGES);
	
	@Override
	public boolean createNewPatient(String id, String name, String age, List<String> address) throws PatientDirectoryFullException, IdAlreadyExistsException, InputConstraintNotAsExceptedException {
		
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0000T));
		Patient newPatient = new Patient();
		LOGGER.info(HPM2000D);
		int patientId = -1;
		int patientAge = -1;
		try {
			patientId = Integer.parseInt(id);
			patientAge = Integer.parseInt(age);
		} catch(Exception e) {
			throw new InputConstraintNotAsExceptedException();
		}
		
		newPatient.setPatientId(patientId);
		newPatient.setPatientName(name);
		newPatient.setPatientAge(patientAge);
		newPatient.setPatientAddress(address);
		
		if (PATIENT_COLLECTION.containsKey(patientId)) {
			LOGGER.error(MESSAGE_BUNDLER.getString(HPM4000E));
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
		if (PATIENT_COLLECTION.size() != 0) {
			LOGGER.info(MESSAGE_BUNDLER.getString(HPM0003T));
			return PATIENT_COLLECTION;
		} else {
			LOGGER.warn(MESSAGE_BUNDLER.getString(HPM3000W));
			throw new NoUserExistsException();
		}
	}

	/**
	 * 
	 */
	@Override
	public Patient updateExistingPatient(String id, String name, String age, List<String> address) throws PatientWithIdNotFoundException, InputConstraintNotAsExceptedException {
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0004T));
		Patient patient;
		int patientId = -1;
		int patientAge = -1;
		try {
			patientId = Integer.parseInt(id);
			patientAge = Integer.parseInt(age);
		} catch(Exception e) {
			throw new InputConstraintNotAsExceptedException();
		}
		if (PATIENT_COLLECTION.containsKey(patientId)) {
			patient = PATIENT_COLLECTION.get(patientId);
			patient.setPatientName(name);
			patient.setPatientAge(patientAge);
			patient.setPatientAddress(address);
			LOGGER.info(MESSAGE_BUNDLER.getString(HPM0005T));
			return PATIENT_COLLECTION.get(patientId);
		}
		LOGGER.error(MESSAGE_BUNDLER.getString(HPM4001E));
		throw new PatientWithIdNotFoundException();
	}

	@Override
	public Patient deletePatient(String id) throws PatientWithIdNotFoundException, InputConstraintNotAsExceptedException {
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0006T));
		int patientId = -1;
		try {
			patientId = Integer.parseInt(id);
		} catch (Exception e) {
			LOGGER.error(MESSAGE_BUNDLER.getString(HPM4001E));
			throw new InputConstraintNotAsExceptedException();
		}
		if (PATIENT_COLLECTION.containsKey(patientId)) {
			Patient deletedPatient =  PATIENT_COLLECTION.remove(patientId);
			LOGGER.info(MESSAGE_BUNDLER.getString(HPM0007T));
			return deletedPatient;
		} else {
			LOGGER.error(MESSAGE_BUNDLER.getString(HPM4001E));
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
