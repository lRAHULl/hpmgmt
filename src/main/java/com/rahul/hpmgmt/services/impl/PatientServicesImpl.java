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
	private static final ResourceBundle MESSAGE_BUNDLER = ResourceBundle.getBundle("messages");
	
	@Override
	public boolean createANewPatient(int id, String name, int age, List<String> address) throws PatientDirectoryFullException, IdAlreadyExistsException {
		
		LOGGER.info("Entered the CreatePatient method");
		Patient newPatient = new Patient();
		LOGGER.info("Created a new patient object");
		
		newPatient.setPatientId(id);
		newPatient.setPatientName(name);
		newPatient.setPatientAge(age);
		newPatient.setPatientAddress(address);
		
		if (PATIENT_ARRAY.containsKey(id)) {
			LOGGER.error("Patient with Id already exists");
			throw new IdAlreadyExistsException();
		} else {
			PATIENT_ARRAY.putIfAbsent(newPatient.getPatientId(), newPatient);
			return true;
		}
	}

	@Override
	public Map<Integer, Patient> readAllPatient() {
		LOGGER.info("In ReadAllPatients method");
		return PATIENT_ARRAY;
	}

	/**
	 * 
	 */
	@Override
	public Patient updateAnExistingPatient(int id, String name, int age, List<String> address) throws PatientWithIdNotFoundException {
		// TODO Auto-generated method stub
		Patient patient;
		if (PATIENT_ARRAY.containsKey(id)) {
			LOGGER.info("Id Of existing User " + PATIENT_ARRAY.get(id));
			patient = PATIENT_ARRAY.get(id);
			LOGGER.info("Id Of user " + Integer.valueOf(patient.getPatientId()));
			patient.setPatientName(name);
			patient.setPatientAge(age);
			patient.setPatientAddress(address);
			return PATIENT_ARRAY.get(id);
		}
		throw new PatientWithIdNotFoundException();
	}

	@Override
	public Patient deleteAPatient(int id) throws PatientWithIdNotFoundException {
		if (PATIENT_ARRAY.containsKey(id)) {
			Patient deletedPatient =  PATIENT_ARRAY.remove(id);
			return deletedPatient;
		} else {
			throw new PatientWithIdNotFoundException();
		}
	}
	
	@Override
	public int findNumberOfPatients() {
		int count = 0;
		
		Iterator<Integer> it = PATIENT_ARRAY.keySet().iterator();
		while(it.hasNext()) {
			count++;
		}
		return count;
	}
}
