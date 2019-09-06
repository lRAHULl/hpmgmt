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
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hospitalmanagement.patient.dao.PatientDAO;
import com.hospitalmanagement.patient.exceptions.FileInputOutputException;
import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientWithIdNotFoundException;
import com.hospitalmanagement.patient.model.Patient;

import static com.hospitalmanagement.patient.constants.LoggerConstants.HPM0000T;
import static com.hospitalmanagement.patient.constants.PatientDAOConstants.*;

/**
 * @author rahul
 *
 */
public class PatientJSONDAOImpl implements PatientDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PatientJSONDAOImpl.class);
	private static final ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle(PATIENT_DAO_MESSAGES);

	@Override
	public boolean createPatient(Patient patient) throws IOException, IdAlreadyExistsException, FileInputOutputException {
		LOGGER.info(MessageFormat.format(MESSAGE_BUNDLE.getString(HPM0000T), patient.toString()));
		File jsonFile = new File(JSON_FILE_PATH);
		
		BufferedReader jsonReader = null;
		BufferedWriter jsonWriter = null;
		
		if (!jsonFile.exists()) {
			
			jsonFile.createNewFile();
		}
		if (!jsonFile.isFile()) {
			LOGGER.error("FileInputOutputException - The given file doesnot contains a file");
			throw new FileInputOutputException("The given path has no file");
		}
		
		if(!jsonFile.canRead()) {
			LOGGER.error("FileInputOutputException - The given file doesnot has read permissions");
			throw new FileInputOutputException("This file has no Read permissions");
		}
		
		if (!jsonFile.canWrite()) {
			LOGGER.error("FileInputOutputException - The given file doesnot has write permissions");
			throw new FileInputOutputException("This file has no write permissions");
		}
		try {
			jsonReader = new BufferedReader(new FileReader(jsonFile));
			LOGGER.debug("BufferedReader object created");
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Type dToListType = new TypeToken<List<Patient>>() {
				private static final long serialVersionUID = 1696515610824017412L;}.getType();
			
			List<Patient> patients = gson.fromJson(jsonReader, dToListType);
			
			if (patients == null) {
				patients = new ArrayList<Patient>();
			}
			
			boolean flag = false;
			for (int looper = 0; looper < patients.size(); looper++) {
				if (patients.get(looper).getPatientId() == patient.getPatientId()) {
					flag = true;
				}
			}
			if (flag) {
				throw new IdAlreadyExistsException("The Id already exists in the directory");
			} else {
				patients.add(patient);
				jsonWriter = new BufferedWriter(new FileWriter(jsonFile));
				LOGGER.debug("BufferedWriter object created");
				gson.toJson(patients, jsonWriter);
				return true;
			}
		} finally {
			if (jsonReader != null) {
				jsonReader.close();
				LOGGER.debug("BufferedReader object closed");
			}
			if (jsonWriter != null)
				jsonWriter.close();
				LOGGER.debug("BufferedWriter object closed");
		}
	}

	@Override
	public List<Patient> readPatients() throws IOException, NoUserExistsException, FileInputOutputException {
		File jsonFile = new File(JSON_FILE_PATH);
		
		BufferedReader jsonReader = null;
		
		if (!jsonFile.exists()) {
			throw new FileInputOutputException("The given file doesnot exists");
		}
		if (!jsonFile.isFile()) {
			throw new FileInputOutputException("The given file doesnot contains a file");
		}
		if(!jsonFile.canRead()) {
			throw new FileInputOutputException("The given file doesnot has read permissions");
		} 
		
		try {
			jsonReader = new BufferedReader(new FileReader(jsonFile));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Type dToListType = new TypeToken<List<Patient>>() {
				private static final long serialVersionUID = 2233773872203003955L;}.getType();
			
			List<Patient> patients = gson.fromJson(jsonReader, dToListType);
			
			if (patients == null) {
				throw new NoUserExistsException();
			} else {
				return patients;
			}
		}catch (Exception e) {
			LOGGER.error("Error Occured: " + e.getMessage());
		} finally {
			if (jsonReader != null)
				jsonReader.close();
		}
		return null;
	}

	@Override
	public Patient updatePatient(int id, Patient newPatient)
			throws IOException, PatientWithIdNotFoundException, NoUserExistsException, FileInputOutputException {
		File jsonFile = new File(JSON_FILE_PATH);
		
		BufferedReader jsonReader = null;
		BufferedWriter jsonWriter = null;
		Patient updatedPatient = null;
		
		if (!jsonFile.exists()) {
			throw new FileInputOutputException("The given file doesnot exists");
		}
		if (!jsonFile.isFile()) {
			throw new FileInputOutputException("The given file doesnot contains a file");
		}
		if(!jsonFile.canRead()) {
			throw new FileInputOutputException("The given file doesnot has read permissions");
		} 
		try {
			jsonReader = new BufferedReader(new FileReader(jsonFile));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Type dToListType = new TypeToken<List<Patient>>() {
				private static final long serialVersionUID = -3337113616305503773L;}.getType();
			
			List<Patient> patients = gson.fromJson(jsonReader, dToListType);
			
			if (patients == null) {
				patients = new ArrayList<Patient>();
			}
			
			boolean flag = false;
			for (int looper = 0; looper < patients.size(); looper++) {
				if (patients.get(looper).getPatientId() == newPatient.getPatientId()) {
					flag = true;
					patients.get(looper).setPatientId(newPatient.getPatientId());
					patients.get(looper).setPatientName(newPatient.getPatientName());
					patients.get(looper).setPatientAge(newPatient.getPatientAge());
					patients.get(looper).setPatientAddress(newPatient.getPatientAddress());
					updatedPatient = patients.get(looper);
				}
			} 
			
			if (flag) {
				if (jsonFile.canWrite()) {
					jsonWriter = new BufferedWriter(new FileWriter(jsonFile));
					gson.toJson(patients, jsonWriter);
					return updatedPatient;
				}
			} else {
				throw new PatientWithIdNotFoundException("ERR404: The patient with the given id not found");
			}
			
		}finally {
			if (jsonReader != null)
				jsonReader.close();
			if (jsonWriter != null)
				jsonWriter.close();
		}
		return null;
	}

	@Override
	public Patient deletePatient(int id)
			throws IOException, PatientWithIdNotFoundException, NoUserExistsException, FileInputOutputException {
		File jsonFile = new File(JSON_FILE_PATH);
		
		BufferedReader jsonReader = null;
		BufferedWriter jsonWriter = null;
		Patient updatedPatient = null;
		
		if (!jsonFile.exists()) {
			throw new FileInputOutputException("The given file doesnot exists");
		}
		if (!jsonFile.isFile()) {
			throw new FileInputOutputException("The given file doesnot contains a file");
		}
		if(!jsonFile.canRead()) {
			throw new FileInputOutputException("The given file doesnot has read permissions");
		}
		try {
			jsonReader = new BufferedReader(new FileReader(jsonFile));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Type dToListType = new TypeToken<List<Patient>>() {
				private static final long serialVersionUID = -6141905780534039833L;
			}.getType();
			
			List<Patient> patients = gson.fromJson(jsonReader, dToListType);
			
			if (patients == null) {
				patients = new ArrayList<Patient>();
			}
			
			boolean flag = false;
			for (int looper = 0; looper < patients.size(); looper++) {
				if (patients.get(looper).getPatientId() == id) {
					flag = true;
					updatedPatient = patients.get(looper);
					patients.remove(looper);
				}
			} 
			
			if (flag) {
				if (jsonFile.canWrite()) {
					jsonWriter = new BufferedWriter(new FileWriter(jsonFile));
					gson.toJson(patients, jsonWriter);
					return updatedPatient;
				}
			} else {
				throw new PatientWithIdNotFoundException("ERR404: The patient with the given id not found");
			}
			
		} finally {
			if (jsonReader != null)
				jsonReader.close();
			if (jsonWriter != null)
				jsonWriter.close();
		}
		return null;
	}

}
