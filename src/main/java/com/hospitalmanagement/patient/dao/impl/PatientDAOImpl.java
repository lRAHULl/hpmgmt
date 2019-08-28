/**
 * 
 */
package com.hospitalmanagement.patient.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hospitalmanagement.patient.dao.PatientDAO;
import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientWithIdNotFoundException;
import com.hospitalmanagement.patient.model.Patient;
import static com.hospitalmanagement.patient.constants.PatientDAOConstants.*;
import static com.hospitalmanagement.patient.constants.PatientConstants.*;

/**
 * @author VC
 *
 */
public class PatientDAOImpl implements PatientDAO {

	public static final Logger LOGGER = LoggerFactory.getLogger(PatientDAOImpl.class);
	public static final ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle(PATIENT_DAO_MESSAGES);
	
	@Override
	public boolean createPatient(Patient patient) throws IOException, IdAlreadyExistsException {
		LOGGER.info(MESSAGE_BUNDLE.getString(HPM0000T));
		try {
			File file = new File(FILE_PATH);
			BufferedWriter createPatientWriter;
			String newPatientWriteLine = "";
			boolean flag = false;
			newPatientWriteLine += patient.getPatientId() + SEPARATOR + patient.getPatientName() + SEPARATOR + patient.getPatientAge() + SEPARATOR  + patient.getPatientAddress().get(0) +SEPARATOR  + patient.getPatientAddress().get(1) + SEPARATOR  + patient.getPatientAddress().get(2) + NEWLINE_SEPARATOR;
			
			if (findNumberOflines() > 0) {
				List<Patient> readResult = getPatientsFromFile();
				for (int looper = 0; looper < readResult.size(); looper++) {
					if (readResult.get(looper).getPatientId() == patient.getPatientId()) {
						flag = true;
					}
				}
				if (flag) {
					throw new IdAlreadyExistsException();
				}
				createPatientWriter = new BufferedWriter(new FileWriter(file, true));
				createPatientWriter.write(newPatientWriteLine);
				createPatientWriter.close();
				return true;
			} else {
				createPatientWriter = new BufferedWriter(new FileWriter(file, true));
				newPatientWriteLine = NEWFILE_HEAD + newPatientWriteLine;
				createPatientWriter.write(newPatientWriteLine);
				createPatientWriter.close();
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Patient> readPatients() throws IOException, NoUserExistsException {
		
		List<Patient> readAllPatientsResults;
		if (findNumberOflines() > 0) {
			readAllPatientsResults = getPatientsFromFile();
			return readAllPatientsResults;
		} else {
			throw new NoUserExistsException();
		}
	}

	@Override
	public Patient updatePatient(int id, Patient newPatient) throws IOException, PatientWithIdNotFoundException, NoUserExistsException {
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
				return returnPatient;
			} else {
				throw new PatientWithIdNotFoundException();
			}
			
			
		} else {
			throw new NoUserExistsException();
		}
	}

	@Override
	public Patient deletePatient(int id) throws IOException, PatientWithIdNotFoundException, NoUserExistsException {
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
					writeLine += loopPatient.getPatientId() + SEPARATOR + loopPatient.getPatientName() + SEPARATOR + loopPatient.getPatientAge() + SEPARATOR  + loopPatient.getPatientAddress().get(0) +SEPARATOR  + loopPatient.getPatientAddress().get(1) + SEPARATOR  + loopPatient.getPatientAddress().get(2) + NEWLINE_SEPARATOR;
					tempFileWriter.write(writeLine);
					writeLine = "";
				}
				tempFileWriter.close();
				file.delete();
				tempFile.renameTo(file);
				return returnPatient;
			} else {
				throw new PatientWithIdNotFoundException();
			}
			
			
		} else {
			throw new NoUserExistsException();
		}
	}
	
	public int findNumberOflines() throws IOException {
		int lines = 0;
		File file = new File(FILE_PATH);
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			while (reader.readLine() != null) {
				lines++;
			}
			reader.close();
			return lines;
		} catch (FileNotFoundException e) {
			return 0;
		}
	}
	
	public List<Patient> getPatientsFromFile() throws IOException {
		File file = new File(FILE_PATH);
		List<Patient> readResult = new ArrayList<>();
		if (findNumberOflines() > 0) {
			Patient patient;
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			reader.readLine();
			while ((line = reader.readLine()) != null) {
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
			reader.close();
		}
		return readResult;
	}
}
