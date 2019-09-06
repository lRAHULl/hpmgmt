/**
 * 
 */
package com.hospitalmanagement.patient.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hospitalmanagement.patient.dao.PatientDAO;
import com.hospitalmanagement.patient.exceptions.FileInputOutputException;
import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientWithIdNotFoundException;
import com.hospitalmanagement.patient.model.Patient;
import static com.hospitalmanagement.patient.constants.PatientDAOConstants.*;
import static com.hospitalmanagement.patient.constants.LoggerConstants.*;

/**
 * @author rahul
 *
 */
public class PatientCSVDAOImpl implements PatientDAO {

	public static final Logger LOGGER = LoggerFactory.getLogger(PatientCSVDAOImpl.class);
	public static final ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle(PATIENT_DAO_MESSAGES);
	
	@Override
	public boolean createPatient(Patient patient) throws IOException, IdAlreadyExistsException, FileInputOutputException {
		LOGGER.info(MessageFormat.format(MESSAGE_BUNDLE.getString(HPM0000T), patient.toString()));
		BufferedWriter createPatientWriter = null;
		
		try {
			File file = new File(FILE_PATH);
			if (!file.exists()) {
				file.createNewFile();
			}
			if (!file.canWrite()) {
				throw new FileInputOutputException("The file in the path can not be written");
			}
			String newPatientWriteLine = "";
			boolean flag = false;
			newPatientWriteLine += patient.getPatientId() 
					+ SEPARATOR + patient.getPatientName() 
					+ SEPARATOR + patient.getPatientAge() 
					+ SEPARATOR  + patient.getPatientAddress().get(0) 
					+ SEPARATOR  + patient.getPatientAddress().get(1)
					+ SEPARATOR  + patient.getPatientAddress().get(2) 
					+ NEWLINE_SEPARATOR;
			
			if (findNumberOflines() > 0) {
				List<Patient> readResult = getPatientsFromFile();
				for (int looper = 0; looper < readResult.size(); looper++) {
					if (readResult.get(looper).getPatientId() == patient.getPatientId()) {
						flag = true;
					}
				}
				if (flag) {
					throw new IdAlreadyExistsException("The Id already exists in the directory");
				}
				createPatientWriter = new BufferedWriter(new FileWriter(file, true));
				LOGGER.debug("The createPatientWriter FileWriter Object has been created");
				createPatientWriter.write(newPatientWriteLine);
				
				LOGGER.info(MessageFormat.format(MESSAGE_BUNDLE.getString(HPM0001T), true));
				return true;
			} else {
				createPatientWriter = new BufferedWriter(new FileWriter(file, true));
				newPatientWriteLine = NEWFILE_HEAD + newPatientWriteLine;
				createPatientWriter.write(newPatientWriteLine);
				LOGGER.info(MessageFormat.format(MESSAGE_BUNDLE.getString(HPM0001T), true));
				return true;
			}
		} finally {
			if (createPatientWriter != null) {
				createPatientWriter.close();
				LOGGER.debug("The createPatientWriter FileWriter Object has been closed");
			}
		}
	}

	@Override
	public List<Patient> readPatients() throws IOException, NoUserExistsException, FileInputOutputException {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPM0002T));
		List<Patient> readAllPatientsResults;
		if (findNumberOflines() > 0) {
			readAllPatientsResults = getPatientsFromFile();
			LOGGER.info(MessageFormat.format(MESSAGE_BUNDLE.getString(HPM0003T), readAllPatientsResults.toString()));
			return readAllPatientsResults;
		} else {
			LOGGER.info("No users are present in the database");
			throw new NoUserExistsException("No users are present in the database");
		}
	}

	@Override
	public Patient updatePatient(int id, Patient newPatient) throws IOException, PatientWithIdNotFoundException, NoUserExistsException, FileInputOutputException {
		LOGGER.info(MessageFormat.format(MESSAGE_BUNDLE.getString(HPM0004T), newPatient.toString()));
		File file = new File(FILE_PATH);
		List<Patient> readAllPatientsResults;
		boolean flag = false;
		if (findNumberOflines() > 0) {
			readAllPatientsResults = getPatientsFromFile();
			Patient returnPatient = null;
			
			for (int looper = 0; looper < readAllPatientsResults.size(); looper++) {
				
				if (readAllPatientsResults.get(looper).getPatientId() == id) {
					flag = true;
					readAllPatientsResults.get(looper).setPatientId(newPatient.getPatientId());
					readAllPatientsResults.get(looper).setPatientName(newPatient.getPatientName());
					readAllPatientsResults.get(looper).setPatientAge(newPatient.getPatientAge());
					readAllPatientsResults.get(looper).setPatientAddress(newPatient.getPatientAddress());
					returnPatient = readAllPatientsResults.get(looper);
				}
			}
			
			if (flag) {
				LOGGER.info("Patient with id found");
				File tempFile = new File(TEMP_FILE_PATH);
				BufferedWriter tempFileWriter = new BufferedWriter(new FileWriter(tempFile));
				tempFileWriter.write(NEWFILE_HEAD);
				Patient loopPatient;
				String writeLine = "";
				for (int looper = 0; looper < readAllPatientsResults.size(); looper++) {
					loopPatient = readAllPatientsResults.get(looper);
					writeLine += loopPatient.getPatientId()
							+ SEPARATOR + loopPatient.getPatientName()
							+ SEPARATOR + loopPatient.getPatientAge()
							+ SEPARATOR  + loopPatient.getPatientAddress().get(0)
							+ SEPARATOR  + loopPatient.getPatientAddress().get(1)
							+ SEPARATOR  + loopPatient.getPatientAddress().get(2)
							+ NEWLINE_SEPARATOR;
					tempFileWriter.write(writeLine);
					writeLine = "";
				}
				tempFileWriter.close();
				file.delete();
				tempFile.renameTo(file);
				LOGGER.info(MessageFormat.format(MESSAGE_BUNDLE.getString(HPM0005T), returnPatient.toString()));
				return returnPatient;
			} else {
				LOGGER.info("No patient with the id " + id + " found");
				throw new PatientWithIdNotFoundException("No patient with the id " + id + " found");
			}			
		} else {
			LOGGER.info("No users are present in the database");
			throw new NoUserExistsException("No users are present in the database");
		}
	}

	@Override
	public Patient deletePatient(int id) throws IOException, PatientWithIdNotFoundException, NoUserExistsException, FileInputOutputException {
		LOGGER.info(MessageFormat.format(MESSAGE_BUNDLE.getString(HPM0006T), id));
		File file = new File(FILE_PATH);
		List<Patient> readResult;
		boolean flag = false;
		if (findNumberOflines() > 0) {
			readResult = getPatientsFromFile();
			Patient returnPatient = null;
			for (int looper = 0; looper < readResult.size(); looper++) {
				
				if (readResult.get(looper).getPatientId() == id) {
					flag = true;
					returnPatient = readResult.get(looper);
					readResult.remove(looper);
				}
			}
			if (flag) {
				File tempFile = new File(TEMP_FILE_PATH);
				BufferedWriter tempFileWriter = new BufferedWriter(new FileWriter(tempFile));
				tempFileWriter.write(NEWFILE_HEAD);
				Patient loopPatient;
				String writeLine = "";
				for (int looper = 0; looper < readResult.size(); looper++) {
					loopPatient = readResult.get(looper);
					writeLine += loopPatient.getPatientId() 
							+ SEPARATOR + loopPatient.getPatientName() 
							+ SEPARATOR + loopPatient.getPatientAge() 
							+ SEPARATOR  + loopPatient.getPatientAddress().get(0) 
							+ SEPARATOR  + loopPatient.getPatientAddress().get(1) 
							+ SEPARATOR  + loopPatient.getPatientAddress().get(2) 
							+ NEWLINE_SEPARATOR;
					tempFileWriter.write(writeLine);
					writeLine = "";
				}
				tempFileWriter.close();
				file.delete();
				tempFile.renameTo(file);
				LOGGER.info(MessageFormat.format(MESSAGE_BUNDLE.getString(HPM0007T), returnPatient.toString()));
				return returnPatient;
			} else {
				LOGGER.info("No patient with the id " + id + " found");
				throw new PatientWithIdNotFoundException("No patient with the id " + id + " found");
			}			
		} else {
			LOGGER.info("No users are present in the database");
			throw new NoUserExistsException("No users are present in the database");
		}
	}
	
	public int findNumberOflines() throws IOException, FileInputOutputException {
		LOGGER.info("Inside the findNumberOfLinesInAFile Utility method");
		int lines = 0;
		File file = new File(FILE_PATH);
		BufferedReader reader = null;
		if (!file.exists()) {
			throw new FileInputOutputException("The given file doesnot exists");
		}
		if (!file.isFile()) {
			throw new FileInputOutputException("The given file doesnot contains a file");
		}
		if(!file.canRead()) {
			throw new FileInputOutputException("The given file doesnot has read permissions");
		} 
		
		try {
			reader = new BufferedReader(new FileReader(file));
			reader.readLine();
			while (reader.readLine() != null) {
				lines++;
			}
			LOGGER.info("Exited the findNumberOfLinesInAFile Utility method with value" + lines);
			return lines;
		} finally {
			if (reader != null)
				reader.close();
		}
	}
	
	public List<Patient> getPatientsFromFile() throws IOException, FileInputOutputException {
		File file = new File(FILE_PATH);
		List<Patient> readResult = new ArrayList<>();
		if (!file.exists()) {
			throw new FileInputOutputException("The given file doesnot exists");
		} else if (!file.isFile()) {
			throw new FileInputOutputException("The given file doesnot contains a file");
		} else if(!file.canRead()) {
			throw new FileInputOutputException("The given file doesnot has read permissions");
		} else {
			if (findNumberOflines() > 0) {
				Patient patient;
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(file));
					LOGGER.debug("The BufferedReader object has been created");
					String line;
					reader.readLine();
					while ((line = reader.readLine()) != null) {
						if (!line.equalsIgnoreCase("")) {
							String[] patientDetails = line.split(SEPARATOR);
							patient = new Patient();
							List<String> address = new ArrayList<String>();
							patient.setPatientId(Integer.parseInt(patientDetails[0]));
							patient.setPatientName(patientDetails[1]);
							patient.setPatientAge(Integer.parseInt(patientDetails[2]));
							address.add(patientDetails[3]);
							address.add(patientDetails[4]);
							address.add(patientDetails[5]);
							patient.setPatientAddress(address);
							System.out.println(patient);
							readResult.add(patient);
						}
					}
				} finally {
					if (reader != null) {
						LOGGER.debug("The BufferedReader object is closed");
						reader.close();
					}
				}
			}
			return readResult;
		}
	}
}
