/**
 * 
 */
package com.hospitalmanagement.patient.services.impl;

import static com.hospitalmanagement.patient.constants.PatientConstants.*;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hospitalmanagement.patient.dao.PatientDAO;
import com.hospitalmanagement.patient.dao.factory.PatientDAOFactory;
import com.hospitalmanagement.patient.dao.impl.PatientCSVDAOImpl;
import com.hospitalmanagement.patient.dao.impl.PatientJSONDAOImpl;
import com.hospitalmanagement.patient.exceptions.FileReadException;
import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.InputConstraintNotAsExceptedException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientDirectoryFullException;
import com.hospitalmanagement.patient.exceptions.PatientWithIdNotFoundException;
import com.hospitalmanagement.patient.model.Patient;
import com.hospitalmanagement.patient.services.PatientServices;

import com.hospitalmanagement.patient.main.Main;

/**
 * @author rahul
 *
 */
public class PatientServicesImpl implements PatientServices {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientServicesImpl.class);
	private static final ResourceBundle MESSAGE_BUNDLER = ResourceBundle.getBundle(LOGGER_MESSAGES);
	
	
	
	PatientDAOFactory factory = new PatientDAOFactory();
	PatientDAO patientDAO = factory.getPatientDataSource(Main.getFactoryType);
	
	@Override
	public boolean createNewPatient(Patient patient) throws PatientDirectoryFullException, IdAlreadyExistsException, InputConstraintNotAsExceptedException {
		
		LOGGER.info(MessageFormat.format(MESSAGE_BUNDLER.getString(HPM0000T), patient.toString()));
		
		try {
			patientDAO.createPatient(patient);
			return true;
		} catch (Exception e) {
			throw new IdAlreadyExistsException();
		}
	}

	@Override
	public List<Patient> readAllPatient() throws NoUserExistsException {
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0002T));

		try {
			List<Patient> results = patientDAO.readPatients();
			return results;
		} catch (Exception e) {
			System.out.println("here");
			System.out.println(e.getClass().getSimpleName());
			throw new NoUserExistsException();
		}
	}

	/**
	 * 
	 */
	@Override
	public Patient updateExistingPatient(Patient patient) throws PatientWithIdNotFoundException, InputConstraintNotAsExceptedException {
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0004T));
		
		try {
			Patient updatedPatient = patientDAO.updatePatient(patient.getPatientId(), patient);
			return updatedPatient;
		} catch (Exception e) {
			LOGGER.error(MESSAGE_BUNDLER.getString(HPM4001E));
			throw new PatientWithIdNotFoundException();
		}
	}

	@Override
	public Patient deletePatient(int id) throws PatientWithIdNotFoundException, InputConstraintNotAsExceptedException {
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0006T));

		try {
			Patient deletedPatient = patientDAO.deletePatient(id);
			LOGGER.info(MESSAGE_BUNDLER.getString(HPM0007T));
			return deletedPatient;
		} catch (Exception e) {
			LOGGER.error(MESSAGE_BUNDLER.getString(HPM4001E));
			throw new PatientWithIdNotFoundException();
		}
	}
	
	@Override
	public int findNumberOfPatients() throws FileReadException, NoUserExistsException {
		
		try {
			return patientDAO.readPatients().size();
		} catch (IOException | NoUserExistsException e) {
			throw new NoUserExistsException();
		}
	}
}
