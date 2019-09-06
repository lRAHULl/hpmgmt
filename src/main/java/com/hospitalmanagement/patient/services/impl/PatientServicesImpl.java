/**
 * 
 */
package com.hospitalmanagement.patient.services.impl;

import static com.hospitalmanagement.patient.constants.LoggerConstants.*;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hospitalmanagement.patient.dao.PatientDAO;
import com.hospitalmanagement.patient.dao.factory.FileType;
import com.hospitalmanagement.patient.dao.factory.PatientDAOFactory;
import com.hospitalmanagement.patient.exceptions.FileInputOutputException;
import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.InputConstraintNotAsExceptedException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
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
	
	PatientDAOFactory factory = new PatientDAOFactory();
	
	PatientDAO patientDAO = null;
	
	public void setFilePath(FileType inputFileType) {
		patientDAO = factory.getPatientDataSource(inputFileType);
	}
	
	
	@Override
	public boolean createNewPatient(Patient patient) throws IdAlreadyExistsException, InputConstraintNotAsExceptedException, IOException, FileInputOutputException {
		
		LOGGER.info(MessageFormat.format(MESSAGE_BUNDLER.getString(HPM0000T), patient.toString()));
		return patientDAO.createPatient(patient);

	}

	@Override
	public List<Patient> readAllPatient() throws NoUserExistsException, IOException, FileInputOutputException {
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0002T));
		List<Patient> results = patientDAO.readPatients();
		return results;
	}

	/**
	 * @throws FileReadException 
	 * @throws NoUserExistsException 
	 * @throws IOException 
	 * @throws FileInputOutputException 
	 * 
	 */
	@Override
	public Patient updateExistingPatient(Patient patient) throws PatientWithIdNotFoundException, InputConstraintNotAsExceptedException, IOException, NoUserExistsException, FileInputOutputException {
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0004T));
		Patient updatedPatient = patientDAO.updatePatient(patient.getPatientId(), patient);
		return updatedPatient;
	}

	@Override
	public Patient deletePatient(int id) throws PatientWithIdNotFoundException, InputConstraintNotAsExceptedException, IOException, NoUserExistsException, FileInputOutputException {
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0006T));
		Patient deletedPatient = patientDAO.deletePatient(id);
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0007T));
		return deletedPatient;
	}
	
	@Override
	public int findNumberOfPatients() throws NoUserExistsException, IOException, FileInputOutputException {
		LOGGER.info(MESSAGE_BUNDLER.getString(HPM0008T));
		LOGGER.info(MessageFormat.format(MESSAGE_BUNDLER.getString(HPM0009T), patientDAO.readPatients().size()));
		return patientDAO.readPatients().size();
	}
		
}
